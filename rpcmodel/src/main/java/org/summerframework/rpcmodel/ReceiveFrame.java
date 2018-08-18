package org.summerframework.rpcmodel;

import org.summerframework.model.LocalSum;
import org.summerframework.model.SummerSum;

public class ReceiveFrame extends SummerSum<Void> implements LocalSum{

    private FrameMessage frameMsg;
    private Object channel;

    public static Object sum(Object channel, FrameMessage frameMsg){
        ReceiveFrame mod = new ReceiveFrame();
        mod.setChannel(channel);
        mod.setFrameMsg(frameMsg);
        return mod.baseSum();
    }

    public FrameMessage getFrameMsg() {
        return frameMsg;
    }

    public void setFrameMsg(FrameMessage frameMsg) {
        this.frameMsg = frameMsg;
    }

    public Object getChannel() {
        return channel;
    }

    public void setChannel(Object channel) {
        this.channel = channel;
    }
}
