package com.theniceidea.summer.springproxyvertx.srv;

import com.theniceidea.summer.model.SummerSum;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class RouterInit implements ApplicationContextAware{

    @Value("${server.context-path}")
    private String contentPath;

    @Value("${summer.vertx.port}")
    private int port;

    @Autowired
    private Vertx vertx;

    @Autowired
    private Router router;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initRouter(applicationContext);
        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }
    private void initRouter(ApplicationContext applicationContext){
        String[] names = applicationContext.getBeanNamesForAnnotation(RestController.class);
        for(int i=0; i<names.length; i++) {
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);

            RequestMapping requestMapping = targetClass.getAnnotation(RequestMapping.class);
            if(null == requestMapping) throw new RuntimeException("RequestMapping not found controller "+targetClass.getName());

            Method[] methods = targetClass.getMethods();
            if(isNull(methods)) continue;

            HashMap<Class<?>, Method> map = getAopMethodsMap(bean);

            for(Method method : methods){
                if(!isSummerStandardMethod(method)) continue;

                PostMapping postMapping  = method.getAnnotation(PostMapping.class);
                if(nonNull(postMapping)){
                    RouterHandlerItem handlerItem = build(map, getPath(requestMapping.value()), getPath(postMapping.value()),
                                                    bean, method);
                    router.post(handlerItem.getPath()).handler(handlerItem);
                    continue;
                }
                DeleteMapping deleteMapping  = method.getAnnotation(DeleteMapping.class);
                if(nonNull(deleteMapping)){
                    RouterHandlerItem handlerItem = build(map, getPath(requestMapping.value()),
                                                    getPath(deleteMapping.value()), bean, method);
                    router.delete(handlerItem.getPath()).handler(handlerItem);
                    continue;
                }
                PutMapping putMapping  = method.getAnnotation(PutMapping.class);
                if(nonNull(putMapping)){
                    RouterHandlerItem handlerItem = build(map, getPath(requestMapping.value()), getPath(putMapping.value()),
                                                    bean, method);
                    router.put(handlerItem.getPath()).handler(handlerItem);
                    continue;
                }
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                if(nonNull(getMapping)){
                    RouterHandlerItem handlerItem = build(map, getPath(requestMapping.value()), getPath(getMapping.value()),
                                                    bean, method);
                    router.get(handlerItem.getPath()).handler(handlerItem);
                    continue;
                }
            }
        }

    }
    private String getPath(String[] strings){
        if(isNull(strings) || strings.length<=0) return "";
        return strings[0];
    }
    private RouterHandlerItem build(HashMap<Class<?>, Method> map, String classPath, String methodPath, Object bean, Method targetMethod){
        String path = Paths.get(contentPath, classPath, methodPath)
            .toString()
            .replace("\\", "/");

        Class<? extends SummerSum> modelClass = (Class<? extends SummerSum>) targetMethod.getParameterTypes()[0];

        RouterHandlerItem routerItem = new RouterHandlerItem();
        routerItem.setBean(bean);
        routerItem.setPath(path);
        routerItem.setMethod(map.get(modelClass));
        routerItem.setModelClass(modelClass);
        return routerItem;
    }
    private boolean isSummerStandardMethod(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(isNull(parameterTypes) || parameterTypes.length != 1) return false;
        Class<?> parameterType = parameterTypes[0];
        if(!SummerSum.class.isAssignableFrom(parameterType)) return false;
        return true;
    }
    private HashMap<Class<?>, Method> getAopMethodsMap(Object bean){
        Method[] methods = bean.getClass().getMethods();
        HashMap<Class<?>, Method> map = new HashMap<>();
        for(int j=0; j<methods.length; j++){
            Method method = methods[j];
            if(!isSummerStandardMethod(method)) continue;
            Class<?>[] types = method.getParameterTypes();
            if(types.length != 1) continue;
            map.put(types[0], method);
        }
        return map;
    }
}
