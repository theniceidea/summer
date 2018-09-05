package org.summerframework.model;

/**
 * @author jerry
 */
@FunctionalInterface
public interface AsyncSummerResult<T> {
    void accept(T result, Exception exception);
}
