package com.theniceidea.summer.core.base;

public interface ExcludeStrategy {
    boolean isExclude(Class<?> cls, String methodName);
}
