package com.theniceidea.summer.core.srv;

import com.theniceidea.summer.model.SummerSum;
import com.theniceidea.summer.model.OptionalSum;

import java.util.HashMap;

import static java.util.Objects.isNull;

class Manager {
    private static HashMap<Class<?>, ServiceItem> services = new HashMap<>();
    protected static <T extends SummerSum> void register(Class<T> cls, ServiceItem<T> serviceItem){
        reg(cls, serviceItem);
    }
    protected static void register(Class<?> cls, ServiceItemImpl serviceItem){
        reg(cls, serviceItem);
    }
    private static void reg(Class<?> cls, Object serviceItem) {
        services.put(cls, (ServiceItem) serviceItem);
    }
    protected static boolean callService(SummerSum dataModel){
        ServiceItem service = services.get(dataModel.getClass());
        if(isNull(service)) {
            if(dataModel instanceof OptionalSum) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
        service.callService(dataModel);
        return true;
    }
}
