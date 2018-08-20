package org.summerframework.restrpc;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.summerframework.model.GetRegistedSummerModels;
import org.summerframework.model.LocalSummer;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcmodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@SummerService
public class RestrpcEntryManager implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private static Logger logger = Logger.getLogger(RestrpcEntryManager.class.getName());

    private static ConcurrentHashMap<String, List<String>> ENDPOINTS = new ConcurrentHashMap<>();

    private static Random random = new Random();

    private int serverPort;

    private RestTemplate restTemplate;

    public RestrpcEntryManager(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.setConnectTimeout(2000)
            .setReadTimeout(2000)
            .build();
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void callRestrpc(CallRestrpcSummer callRestrpcSummer) throws URISyntaxException {
        Summer summer = callRestrpcSummer.getSummer();
        Class<? extends Summer> cls = summer.getClass();

        if(LocalSummer.class.isAssignableFrom(cls)) {
            throw new RuntimeException("这个summer继承与 LocalSummer, 不允许执行 restrpc 调用;");
        }
        if(!Serializable.class.isAssignableFrom(cls)) {
            throw new RuntimeException("如果要执行rpc调用,请继承与这个接口 Serializable;");
        }

        String jsonString = JSON.toJSONString(summer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("restrpc-class", cls.getName());

        String url = GetRestrpcEntry.sum(summer);
        if(StringUtils.isEmpty(url)) { throw new RuntimeException(" 没有查找到远程的rpc服务 "+cls.getName()); }

        RequestEntity<String> requestEntity = new RequestEntity<>(jsonString, headers, HttpMethod.POST, new URI(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            String errCode = responseEntity.getHeaders().getFirst("restrpc-errcode");
            String errMsg = responseEntity.getHeaders().getFirst("restrpc-errmsg");
            String body = responseEntity.getBody();

            CallSummerResult result = new CallSummerResult();
            result.setErrCode(errCode);
            result.setErrMsg(errMsg);
            result.setResult(body);

            callRestrpcSummer.setResult(result);

            return;
        }

        throw new RuntimeException(" 执行rpc服务异常 httpStatus: "+responseEntity.getStatusCode());
    }
    public void reveiveRestrpc(ReceiveRestrpcSummer receiveRestrpcSummer) throws ClassNotFoundException, IOException {
        HttpServletRequest request = (HttpServletRequest) receiveRestrpcSummer.getRequest();
        HttpServletResponse response = (HttpServletResponse) receiveRestrpcSummer.getResponse();

        String className = request.getHeader("restrpc-class");
        String body = IOUtils.toString(request.getInputStream(), Charsets.toCharset("UTF-8"));

        Class<?> kls = Class.forName(className);
        Summer summer = (Summer)JSON.parseObject(body, kls);

        CallSummerResult result = CallSummer.sum(summer);

        response.addHeader("restrpc-errcode", result.getErrCode());
        response.addHeader("restrpc-errmsg", result.getErrMsg());
        if(nonNull(result.getResult())) {
            if("0".equals(result.getErrCode())) {
                HashMap<String, Object> map = new HashMap<>(1);
                map.put("result", result.getResult());
                response.getWriter()
                    .write(JSON.toJSONString(map));
            }else{
                response.getWriter()
                    .write((String) result.getResult());
            }
        }
    }

    public void getRestrpcEntry(GetRestrpcEntry model){
        Summer<?> summer = model.getSummer();
        String className = summer.getClass().getName();

        if(!ENDPOINTS.containsKey(className)){
            Set<String> strings = GetRestrpcEntries.sum(className);
            if(CollectionUtils.isEmpty(strings)){
                ENDPOINTS.put(className, new ArrayList<>(0));
            }else{
                List<String> list = strings.stream().map(s->{
                    int i = s.indexOf(";");
                    return s.substring(i+1);
                }).collect(Collectors.toList());
                ENDPOINTS.put(className, list);
            }
        }
        List<String> rpcEntries = ENDPOINTS.get(className);
        if(CollectionUtils.isEmpty(rpcEntries)){
            return;
        }

        int i = random.nextInt(rpcEntries.size());
        model.setResult(rpcEntries.get(i));
    }
    public void getRestrpcEntries(GetRestrpcEntries model){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Set<String> keys = redisTemplate.keys("summer-rpc-"+model.getClassName() + "*");
        List<String> strings = ops.multiGet(keys);
        if(CollectionUtils.isEmpty(strings)) {
            model.setResult(new HashSet<>(0));
            return;
        }
        model.setResult(new HashSet<>(strings));
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void regLocalServices() throws SocketException {
        logger.info("regLocalServices");
        String localIpAddr = getLocalIpAddr();
        if(StringUtils.isEmpty(localIpAddr)){
            logger.warning("没有获得ip地址,服务注册失败");
        }

        Set<Class<?>> classes = GetRegistedSummerModels.sum();
        HashMap<String, String> map = new HashMap<>();
        classes.forEach(cls->{
            if(LocalSummer.class.isAssignableFrom(cls)) { return; }
            if(!Serializable.class.isAssignableFrom(cls)) { return; }

            String clsName = cls.getName();
            map.put("summer-rpc-"+clsName+":"+localIpAddr+":"+this.serverPort, clsName+";http://"+localIpAddr+":"+this.serverPort+"/summerentry");
        });
        redisTemplate.executePipelined((RedisCallback<Boolean>) redisConnection -> {
            map.forEach((k, v)->{
                redisConnection.set(k.getBytes(), v.getBytes(), Expiration.seconds(5), RedisStringCommands.SetOption.UPSERT);
            });
            return null;
        });

    }
    @Scheduled(cron = "0/1 * * * * ?")
    public void updateRemortServices() throws SocketException {
        logger.info("updateRemortServices");

        Set<String> keys = redisTemplate.keys("summer-rpc-*");
        List<String> list = redisTemplate.opsForValue()
            .multiGet(keys);
        HashMap<String, List<String>> map = new HashMap<>();
        list.forEach(addr->{
            int i = addr.indexOf(";");
            String clsName = addr.substring(0, i);
            String address = addr.substring(i+1);
            List<String> valueList = map.get(clsName);
            if(isNull(valueList)){
                valueList = new ArrayList<>();
                map.put(clsName, valueList);
            }
            valueList.add(address);
        });
        ENDPOINTS.entrySet().forEach(e->{
            String k = e.getKey();
            List<String> valueList = map.get(k);
            if(CollectionUtils.isEmpty(valueList)) {
                ENDPOINTS.put(k, new ArrayList<>(0));
                return;
            }

            ENDPOINTS.put(k, valueList);
        });
    }
    private static String getLocalIpAddr() throws SocketException {
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            if("lo".equals(netInterface.getName())) { continue; }
            if(netInterface.getName().startsWith("docker")) { continue; }

            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address)
                {
                    return ip.getHostAddress();
                }
            }
        }
        return "";
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.serverPort = event.getEmbeddedServletContainer().getPort();
    }
}
