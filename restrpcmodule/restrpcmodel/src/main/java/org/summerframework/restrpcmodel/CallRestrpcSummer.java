package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

public class CallRestrpcSummer extends Summer<CallSummerResult>{
    private Summer summer;

    public static CallSummerResult sum(Summer summer){
        CallRestrpcSummer mod = new CallRestrpcSummer();

        mod.setSummer(summer);

        return mod.sum();
    }

    public Summer getSummer() {
        return summer;
    }

    public void setSummer(Summer summer) {
        this.summer = summer;
    }

}
