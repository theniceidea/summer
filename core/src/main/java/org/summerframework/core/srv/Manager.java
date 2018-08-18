package org.summerframework.core.srv;

import org.summerframework.model.RemoteServiceSummer;
import org.summerframework.model.LocalSummer;
import org.summerframework.model.Summer;
import org.summerframework.model.OptionalSummer;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

class Manager {
    private static final Logger logger = Logger.getLogger(Manager.class.getName());
    protected static HashMap<Class<?>, ServiceItem<?>> localServices = new HashMap<>();
    protected static ServiceItem remoteServiceItem;

    protected static <T extends Summer<?>> void register(Class<T> cls, ServiceItem<T> serviceItem){
        reg(cls, serviceItem);
    }
    protected static void register(Class<?> cls, ServiceItemImpl serviceItem){
        logger.info("register summmer: "+cls.getName()+", service:"+serviceItem.getTarget().getClass().getName()+" method:"+serviceItem.getMethod().getName());
        reg(cls, serviceItem);
    }
    private static void reg(Class<?> cls, Object serviceItem) {
        if(localServices.containsKey(cls)) throw new RuntimeException("service exists: "+cls.getName());
        localServices.put(cls, (ServiceItem) serviceItem);
        if(Objects.equals(RemoteServiceSummer.class, cls)) remoteServiceItem = (ServiceItem) serviceItem;
    }
    protected static boolean callService(Summer<?> dataModel){
        ServiceItem service = localServices.get(dataModel.getClass());
        if(nonNull(service)) {
            service.callService(dataModel);
            return true;
        }
        if(dataModel instanceof LocalSummer){
            if(dataModel instanceof OptionalSummer) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
        if(nonNull(remoteServiceItem)){
            RemoteServiceSummer model = RemoteServiceSummer.New(dataModel);
            remoteServiceItem.callService(model);
            return model.isProcessed();
        }else{
            if(dataModel instanceof OptionalSummer) return false;
            throw new RuntimeException("service " + dataModel.getClass()
                .getName() + " not found");
        }
    }
}
