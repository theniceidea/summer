package org.summerframework.core.srv;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.summerframework.core.base.EnableSummer;
import org.summerframework.core.base.ExcludeStrategy;
import org.summerframework.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
class ServiceInstall implements ApplicationContextAware{

    private static Logger logger = Logger.getLogger(ServiceInstall.class.getName());

    protected static HashMap<Class<?>, SummerServiceBean<?>> localServices = new HashMap<>();

    public static boolean installed(Class<?> kls){
        return localServices.containsKey(kls);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        logger.info("======install summer=================================================");
        Set<String> excludes = excludePackages(applicationContext);
        List<ExcludeStrategy> strategyBeans = getStrategyBeans(applicationContext);

        String[] names = applicationContext.getBeanNamesForAnnotation(SummerService.class);
        for(int i=0; i<names.length; i++){
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();
            SummerService annotation = targetClass.getAnnotation(SummerService.class);
            if(null == annotation || !annotation.value()) continue;

            HashMap<Class<?>, Method> map = getAopMethodsMap(bean);

            Method[] methods = targetClass.getMethods();
            for(int j=0; j<methods.length; j++){
                Method method = methods[j];
                if(!isSummerStandardMethod(method)) continue;

                SummerService summerService = method.getAnnotation(SummerService.class);
                if(nonNull(summerService) && !summerService.value()) continue;
                Class<?>[] types = method.getParameterTypes();
//                if(types.length != 1) continue;
                Method method1 = map.get(types[0]);
                if(null == method1) continue;
                if(isExclude(excludes, targetClass)) continue;
                if(isExclude(strategyBeans, targetClass, method.getName())) continue;

                install((Class<? extends Summer>) types[0], targetClass, bean, method1);
            }
        }
        logger.info("======install summer=================================================");
    }
    private void install(Class<? extends Summer> modelClass, Class<?> beanClass, Object bean, Method method) {
        try {
            if(Summer.class.equals(modelClass)) return;
            logger.info("install summer class:"+modelClass.getName()+"; classLoader:"+modelClass.getClassLoader());
            Field service = modelClass.getDeclaredField("SERVICE");
            service.setAccessible(true);
            if(SummerServiceBean.class.isAssignableFrom(beanClass) && "sum".equals(method.getName())){
                Type[] genericInterfaces = beanClass.getGenericInterfaces();
                for(Type face : genericInterfaces){
                    ParameterizedType ptype =  (ParameterizedType)face;
                    if(!ptype.getRawType().equals(SummerServiceBean.class)){ continue;}
                    if(ptype.getActualTypeArguments()[0].equals(modelClass)){
                        service.set(null, bean);
                        logger.info("install summer class:"+modelClass.getName()+"; bean:"+bean.getClass()+"; method:");
                        putLocalServices(modelClass, (SummerServiceBean<?>) bean);
                        return;
                    }
                }
            }
            MethodService methodService = new MethodService(bean, method);
            service.set(null, methodService);
            logger.info("install summer class:"+modelClass.getName()+"; bean:"+bean.getClass()+"; method:"+method.getName());
            putLocalServices(modelClass, methodService);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warning("install summer exception, msg:"+e.getMessage()+", exception class:"+e.getClass().getName()+", summer classs:"+modelClass.getName()+", bean: "+bean.getClass()+", method:"+method.getName());
            throw new RuntimeException(e);
        }

    }
    private void putLocalServices(Class<?> kls, SummerServiceBean<?> bean){
        if(localServices.containsKey(kls)){
            throw new RuntimeException("summer service "+kls.getName()+" installed;");
        }
        localServices.put(kls, bean);
    }
    private boolean isSummerStandardMethod(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(isNull(parameterTypes) || parameterTypes.length != 1) return false;
        Class<?> parameterType = parameterTypes[0];
        if(!Summer.class.isAssignableFrom(parameterType)) return false;
        return true;
    }
    private HashMap<Class<?>, Method> getAopMethodsMap(Object bean){
        Method[] methods = bean.getClass().getMethods();
        HashMap<Class<?>, Method> map = new HashMap<>();
        for(int j=0; j<methods.length; j++){
            Method method = methods[j];
            if(!isSummerStandardMethod(method)) continue;
            Class<?>[] types = method.getParameterTypes();
//            if(types.length != 1) continue;
            map.put(types[0], method);
        }
        return map;
    }
    private List<ExcludeStrategy> getStrategyBeans(ApplicationContext applicationContext){
        List<ExcludeStrategy> list = new ArrayList<>();
        String[] names = applicationContext.getBeanNamesForAnnotation(ExcludeStrategyClass.class);
        for(int i=0; i<names.length; i++) {
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            list.add((ExcludeStrategy) bean);
        }
        return list;
    }
    private boolean isExclude(List<ExcludeStrategy> strategies, Class<?> kls, String methodName){
        if(isNull(strategies)) return false;
        for(ExcludeStrategy strategy : strategies){
            if(strategy.isExclude(kls, methodName)) return true;
        }
        return false;
    }
    private boolean isExclude(Set<String> excludes, Class<?> kls){
        String name = kls.getName();
        for (String s : excludes) {
            if(name.startsWith(s)) return true;
        }
        return false;
    }

    private EnableSummer getEnableSummer(ApplicationContext applicationContext){
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(EnableSummer.class);
        Optional<Object> bean = beans.values()
            .stream()
            .findFirst();
        Class<?> targetClass = AopUtils.getTargetClass(bean.get());
        if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();

        return targetClass.getAnnotation(EnableSummer.class);
    }

    private Set<String> excludePackages(ApplicationContext applicationContext){
        HashSet<String> set = new HashSet<>();
        EnableSummer annotation = getEnableSummer(applicationContext);
        String[] strings = annotation.excludePackages();
        if(nonNull(strings)) {
            set.addAll(Arrays.asList(strings));
        }
        return set;
    }
}
