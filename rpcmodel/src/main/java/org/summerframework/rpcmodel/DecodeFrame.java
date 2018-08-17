package org.summerframework.rpcmodel;

import org.summerframework.model.LocalSum;
import org.summerframework.model.SummerSum;

public class DecodeFrame extends SummerSum<Object> implements LocalSum{
    private Object frameMsg;

    public static Object sum(Object frameMsg){
        DecodeFrame mod = new DecodeFrame();
        mod.setFrameMsg(frameMsg);
        return mod.baseSum();
    }

    public Object getFrameMsg() {
        return frameMsg;
    }

    public void setFrameMsg(Object frameMsg) {
        this.frameMsg = frameMsg;
    }
}
