package org.summerframework.rpc.srv;

import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.rpcmodel.FrameMessage;
import org.summerframework.rpcmodel.MetaData;
import org.summerframework.rpcmodel.ParseJson;
import org.summerframework.rpcmodel.ReceiveFrame;

@Service
@SummerService
public class ReceiveFrameService {
    public void receiveFrame(ReceiveFrame receiveFrame){
        FrameMessage msg = (FrameMessage) receiveFrame.getFrameMsg();
        String meta = new String(msg.getMeta());

        MetaData metaData = (MetaData)ParseJson.sum(MetaData.class, meta);

        Class<?> kls = getClass(metaData.getClassName());
        Object body = ParseJson.sum(kls, new String(msg.getBody()));
        
    }

    private Class<?> getClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
