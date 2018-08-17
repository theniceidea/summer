package org.summerframework.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.summerframework.rpcmodel.CreateHeartBeatFrame;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NettyClient extends ChannelHandle {

    private static Logger logger = Logger.getLogger(NettyClient.class.getName());

    private Bootstrap bootstrap;
    private int workThreads = 0;
    private int maxPackageSize = 1024*1024;

    private void init(){
        bootstrap = new Bootstrap();
        ThreadFactory threadFactory = new ThreadFactory("netty-client-");
        NioEventLoopGroup workGroup = new NioEventLoopGroup(workThreads, threadFactory);
        bootstrap
            .group(workGroup)
            .channel(NioServerSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    ChannelPipeline pipeline = sc.pipeline();
                    pipeline.addLast("length-frame-decoder", new LengthFieldBasedFrameDecoder(maxPackageSize, 0, 4, 0, 4));
                    pipeline.addLast("timeout", new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                    pipeline.addLast("handle", new NettyClient());
                }
            });

        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 20000);

        logger.info("netty client start");

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.ALL_IDLE) {
                Channel ch = ctx.channel();
                Object heartBeatFrame = CreateHeartBeatFrame.sum();
                ch.writeAndFlush(heartBeatFrame);
            }
        }

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}
