package org.summerframework.core.srv;

import org.summerframework.core.base.RemoteServiceSum;
import org.summerframework.model.LocalSum;
import org.summerframework.model.SummerSum;
import org.summerframework.model.OptionalSum;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

class Manager {
    private static final Logger logger = Logger.getLogger(Manager.class.getName());
    private static HashMap<Class<?>, ServiceItem<?>> localServices = new HashMap<>();
    private static ServiceItem remoteServiceItem;

    protected static <T extends SummerSum<?>> void register(Class<T> cls, ServiceItem<T> serviceItem){
        reg(cls, serviceItem);
    }
    protected static void register(Class<?> cls, ServiceItemImpl serviceItem){
        logger.info("register summmer: "+cls.getName()+", service:"+serviceItem.getTarget().getClass().getName()+" method:"+serviceItem.getMethod().getName());
        reg(cls, serviceItem);
    }
    private static void reg(Class<?> cls, Object serviceItem) {
        if(localServices.containsKey(cls)) throw new RuntimeException("service exists: "+cls.getName());
        localServices.put(cls, (ServiceItem) serviceItem);
        if(Objects.equals(RemoteServiceSum.class, cls)) remoteServiceItem = (ServiceItem) serviceItem;
    }
    protected static boolean callService(SummerSum<?> dataModel){
        ServiceItem service = localServices.get(dataModel.getClass());
        if(nonNull(service)) {
            service.callService(dataModel);
            return true;
        }
        if(dataModel instanceof LocalSum){
            if(dataModel instanceof OptionalSum) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
        if(nonNull(remoteServiceItem)){
            RemoteServiceSum model = RemoteServiceSum.New(dataModel);
            remoteServiceItem.callService(model);
            return model.isProcessed();
        }else{
            if(dataModel instanceof OptionalSum) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
    }
}
