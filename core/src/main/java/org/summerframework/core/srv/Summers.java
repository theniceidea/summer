package org.summerframework.core.srv;

import org.summerframework.model.SummerSum;

public class Summers {
    public static boolean sum(SummerSum model){
        return Manager.callService(model);
    }
}
