package org.summerframework.rpc.srv;


import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.model.SummerSum;
import org.summerframework.rpcmodel.DecodeFrame;
import org.summerframework.rpcmodel.FrameMessage;
import org.summerframework.rpcmodel.MetaData;
import org.summerframework.rpcmodel.ParseJson;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Service
@SummerService
public class DecodeFrameService {
    private static Logger logger = Logger.getLogger(DecodeFrameService.class.getName());

    public void decodeFrame(DecodeFrame decodeFrame){
        ByteBuf buf = (ByteBuf)decodeFrame.getFrameMsg();
        int metaLength = buf.readInt();
        byte[] metaBytes = new byte[metaLength];
        buf.readBytes(metaBytes);
        int bodyLength = buf.readInt();
        byte[] bodyBytes = new byte[bodyLength];
        buf.readBytes(bodyBytes);

        FrameMessage frame = new FrameMessage();

        String meta = new String(metaBytes);
        MetaData metaData = (MetaData) ParseJson.sum(MetaData.class, meta);

        Class<?> kls = getClass(metaData.getClassName());
        String bodyText = new String(bodyBytes);
        Object body = ParseJson.sum(kls, bodyText);
        if(nonNull(body) && body instanceof SummerSum) {
            frame.setMeta(metaData);
            frame.setBody((SummerSum<?>) body);
            decodeFrame.setResult(frame);
        }

        logger.log(Level.WARNING, "frame body is not Summer Object "+meta+"; body:"+bodyText);

    }

    private Class<?> getClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
