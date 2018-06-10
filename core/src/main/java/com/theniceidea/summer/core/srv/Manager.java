package com.theniceidea.summer.core.srv;

import java.lang.reflect.Field;

import static java.util.Objects.isNull;

class Manager {
    protected static <T extends AbsModel> void register(Class<T> cls, ServiceItem<T> serviceItem){
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
    protected static void callService(AbsModel dataModel){
        ServiceItemImpl service = (ServiceItemImpl) dataModel.target();
        if(isNull(service)) {
            if(dataModel instanceof OptionalServiceModel) return;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
        service.callService(dataModel);
    }
}
