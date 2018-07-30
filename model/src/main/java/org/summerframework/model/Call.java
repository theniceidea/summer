package org.summerframework.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

class Call {
    private static Method callServiceMethod;

    static {
        if(null == callServiceMethod) {
            Class<?> aClass = forName("org.summerframework.core.srv.Manager");
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for(Method method : declaredMethods){
                if(Objects.equals("callService", method.getName())){
                    method.setAccessible(true);
                    callServiceMethod = method;
                }
            }
        }
    }
    private static Class<?> forName(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean call(SummerSum model)
        throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        return (boolean) callServiceMethod.invoke(null, model);
    }

}
