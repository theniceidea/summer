package com.theniceidea.summer.base;

public interface ExcludeStrategy {
    boolean isExclude(Class<?> cls, String methodName);
}
