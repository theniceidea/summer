package org.summerframework.rpcmodel;

import org.summerframework.model.LocalSum;
import org.summerframework.model.SummerSum;

public class CreateHeartBeatFrame extends SummerSum<Object> implements LocalSum{
    public static Object sum(){
        CreateHeartBeatFrame mod = new CreateHeartBeatFrame();
        return mod.baseSum();
    }
}
