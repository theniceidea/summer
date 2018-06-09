package com.theniceidea.summer.srv;

import com.theniceidea.summer.base.EnableSummer;
import com.theniceidea.summer.base.SummerService;
import com.theniceidea.summer.base.SummerServiceClass;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Component
public class ServiceInit implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> excludes = excludePackages(applicationContext);

        String[] names = applicationContext.getBeanNamesForAnnotation(SummerServiceClass.class);
        for(int i=0; i<names.length; i++){
            String beanName = names[i];
            Object bean = applicationContext.getBean(beanName);
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Method[] methods = bean.getClass().getMethods();
            HashMap<Class<?>, Method> map = new HashMap<>();
            for(int j=0; j<methods.length; j++){
                Method method = methods[j];
                Class<?>[] types = method.getParameterTypes();
                if(types.length != 1) continue;
                map.put(types[0], method);
            }
            methods = targetClass.getMethods();
            for(int j=0; j<methods.length; j++){
                Method method = methods[j];
                SummerService summerService = method.getAnnotation(SummerService.class);
                if(null == summerService) continue;
                Class<?>[] types = method.getParameterTypes();
                if(types.length != 1) continue;
                Method method1 = map.get(types[0]);
                if(null == method1) continue;
                if(isExclude(excludes, types[0])) continue;
                Manager.registeService(types[0], new ServiceItem(bean, method1));
            }
        }
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
