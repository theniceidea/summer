package org.summerframework.rpc.srv;


import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.rpcmodel.DecodeFrame;
import org.summerframework.rpcmodel.FrameMessage;

@Service
@SummerService
public class DecodeFrameService {
    public void decodeFrame(DecodeFrame decodeFrame){
        ByteBuf buf = (ByteBuf)decodeFrame.getFrameMsg();
        int metaLength = buf.readInt();
        byte[] metaBytes = new byte[metaLength];
        buf.readBytes(metaBytes);
        int bodyLength = buf.readInt();
        byte[] bodyBytes = new byte[bodyLength];
        buf.readBytes(bodyBytes);

        FrameMessage frame = new FrameMessage();
        frame.setMeta(metaBytes);
        frame.setBody(bodyBytes);

        decodeFrame.setResult(frame);
    }
}
