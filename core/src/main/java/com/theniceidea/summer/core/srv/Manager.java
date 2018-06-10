package com.theniceidea.summer.core.srv;

import java.util.HashMap;

import static java.util.Objects.isNull;

class Manager {
    private static HashMap<Class<?>, ServiceItem> services = new HashMap<>();
    protected static <T extends AbsModel> void register(Class<T> cls, ServiceItem<T> serviceItem){
        reg(cls, serviceItem);
    }
    protected static void register(Class<?> cls, ServiceItemImpl serviceItem){
        reg(cls, serviceItem);
    }
    private static void reg(Class<?> cls, Object serviceItem) {
        services.put(cls, (ServiceItem) serviceItem);
    }
    protected static boolean callService(AbsModel dataModel){
        ServiceItem service = services.get(dataModel.getClass());
        if(isNull(service)) {
            if(dataModel instanceof OptionalServiceModel) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
        service.callService(dataModel);
        return true;
    }
}
