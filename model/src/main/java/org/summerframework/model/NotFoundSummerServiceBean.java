package org.summerframework.model;

public class NotFoundSummerServiceBean implements SummerServiceBean<Object>{

    @Override
    public Object sum(Summer<Object> summer) {
        throw new RuntimeException("service "+summer.getClass().getName()+" not found;");
    }
}
