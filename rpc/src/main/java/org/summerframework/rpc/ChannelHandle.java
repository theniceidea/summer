package org.summerframework.rpc;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import org.summerframework.rpcmodel.DecodeFrame;
import org.summerframework.rpcmodel.ReceiveFrame;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class ChannelHandle extends ChannelDuplexHandler {
    private static Logger logger = Logger.getLogger(ChannelHandle.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object decodedMsg = null;
        try{
            decodedMsg = DecodeFrame.sum(msg);
            if(isNull(decodedMsg)) { return; }
        }finally {
            ReferenceCountUtil.release(msg);
        }

        try {
            ReceiveFrame.sum(ctx.channel(), decodedMsg);
        }catch (Exception e){
            ctx.close();
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

}
