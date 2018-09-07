package org.summerframework.core.srv;

import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodService implements SummerServiceBean {
    private Object target;
    private Method method;

    public MethodService(Object target, Method method){
        this.target = target;
        this.method = method;
    }

    @Override
    public void sum(Summer summer) {
        try {
            this.method.invoke(target, summer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwable cause = e.getCause();
            if(null != cause && cause instanceof RuntimeException){
                throw (RuntimeException)cause;
            }else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}
