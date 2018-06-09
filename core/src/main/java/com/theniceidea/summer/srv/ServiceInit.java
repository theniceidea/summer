package com.theniceidea.summer.srv;

import com.theniceidea.summer.base.SummerService;
import com.theniceidea.summer.base.SummerServiceClass;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

@Component
public class ServiceInit implements ApplicationContextAware{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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
                Manager.registeService(types[0], new ServiceItem(bean, method1));
            }
        }
    }
}
