package org.summerframework.model;

public interface SummerServiceBean<T> {
    T sum(Summer<T> summer);
}
