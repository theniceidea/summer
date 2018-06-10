package com.theniceidea.summer.srv;

import java.lang.reflect.Field;

import static java.util.Objects.isNull;

class Manager {
    protected static <T extends DataModel> void register(Class<T> cls, ServiceItem<T> serviceItem){
        reg(cls, serviceItem);
    }
    protected static void register(Class<?> cls, ServiceItemImpl serviceItem){
        reg(cls, serviceItem);
    }
    private static void reg(Class<?> cls, Object serviceItem) {
        try {
            Field field = cls.getDeclaredField("target");
            field.setAccessible(true);
            field.set(null, serviceItem);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    protected static void callService(DataModel dataModel){
        ServiceItemImpl service = (ServiceItemImpl) dataModel.target();
        if(isNull(service))
            throw new RuntimeException("service "+dataModel.getClass().getName()+" not found");
        service.callService(dataModel);
    }
}
