package com.theniceidea.summer.core.srv;

import com.theniceidea.summer.model.SummerSum;

public class Summers {
    public static boolean sum(SummerSum model){
        return Manager.callService(model);
    }
}
