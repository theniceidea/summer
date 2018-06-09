package com.theniceidea.summer.srv;

import com.theniceidea.summer.base.ServiceItem;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class Manager {
    private static HashMap<String, ServiceItem> services = new HashMap<>();

    public static <T extends DataModel> void registeService(String key, ServiceItem<T> serviceItem){
        if(services.containsKey(key)) throw new RuntimeException("service "+key+" exists;");
        services.put(key, serviceItem);
    }
    public static <T extends DataModel> void registeService(Class<T> cls, ServiceItem<T> serviceItem){
        registeService(cls.getName(), serviceItem);
    }
    public static void removeService(String key){
        services.remove(key);
    }
    protected static void execService(DataModel dataModel){
        ServiceItem serviceItem = services.get(dataModel.getServiceName());
        if(isNull(serviceItem)) throw new RuntimeException("service "+dataModel.getServiceName()+" not found;");
        serviceItem.callService(dataModel);
    }
}
