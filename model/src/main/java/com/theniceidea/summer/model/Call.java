package com.theniceidea.summer.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

class Call {
    private static Method callServiceMethod;

    static boolean call(AbsModel model)
        throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        if(null != callServiceMethod) return (boolean) callServiceMethod.invoke(null, model);
        synchronized (Call.class){
            if(null == callServiceMethod) {
                Class<?> aClass = Class.forName("com.theniceidea.summer.core.srv.Manager");
                Method[] declaredMethods = aClass.getDeclaredMethods();
                for(Method method : declaredMethods){
                    if(Objects.equals("callService", method.getName())){
                        method.setAccessible(true);
                        callServiceMethod = method;
                    }
                }
            }
        }
        return (boolean) callServiceMethod.invoke(null, model);
    }

}
