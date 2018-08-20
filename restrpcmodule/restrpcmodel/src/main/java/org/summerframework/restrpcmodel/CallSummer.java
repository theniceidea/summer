package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

public class CallSummer extends Summer<CallSummerResult>{
    private Summer summer;

    public static CallSummerResult sum(Summer summer){
        CallSummer callSummer = new CallSummer();
        callSummer.setSummer(summer);
        return callSummer.baseSum();
    }

    public Summer getSummer() {
        return summer;
    }

    public void setSummer(Summer summer) {
        this.summer = summer;
    }
}
