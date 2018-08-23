package org.summerframework.model;

public interface SummerServiceBean<T extends Summer> {
    void sum(T summer);
}
