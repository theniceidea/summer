package org.summerframework.model;

public class NotFoundSummerServiceBean implements SummerServiceBean<Summer>{

    @Override
    public void sum(Summer summer) {
        throw new RuntimeException("service "+summer.getClass().getName()+" not found;");
    }
}
