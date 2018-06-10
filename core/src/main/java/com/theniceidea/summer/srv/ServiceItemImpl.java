package com.theniceidea.summer.srv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ServiceItemImpl implements ServiceItem{
    private Object target;
    private Method method;
    public ServiceItemImpl(Object target, Method method){
        this.target = target;
        this.method = method;
    }

    @Override
    public void callService(AbsModel dataModel) {
        try {
            this.method.invoke(target, dataModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
