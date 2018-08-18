package org.summerframework.core.srv;

import org.summerframework.model.ExcludeStrategyClass;
import org.summerframework.model.SummerService;
import org.summerframework.core.base.*;
import org.summerframework.model.Summer;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
class ServiceInit implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> excludes = excludePackages(applicationContext);
        List<ExcludeStrategy> strategyBeans = getStrategyBeans(applicationContext);

        String[] names = applicationContext.getBeanNamesForAnnotation(SummerService.class);
        for(int i=0; i<names.length; i++){
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();
            SummerService annotation = targetClass.getAnnotation(SummerService.class);
            if(!annotation.value()) continue;

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
                Manager.register(types[0], new ServiceItemImpl(bean, method1));
            }
        }
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

    private Set<String> excludePackages(ApplicationContext applicationContext){
        try {
            HashSet<String> set = new HashSet<>();
            String[] names = applicationContext.getBeanNamesForAnnotation(
                (Class<? extends Annotation>) Class.forName("org.springframework.boot.autoconfigure.SpringBootApplication"));
            for(int i=0; i<names.length; i++){
                Object bean = applicationContext.getBean(names[0]);
                Class<?> targetClass = AopUtils.getTargetClass(bean);
                if(targetClass.getName().contains("$$")) targetClass = targetClass.getSuperclass();
                EnableSummer annotation = targetClass.getAnnotation(EnableSummer.class);
                String[] strings = annotation.excludePackages();
                if(isNull(strings)) continue;
                set.addAll(Arrays.asList(strings));
            }
            return set;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
