package org.summerframework.model;

public class AsyncSummerResult<T> {
    private T result;
    private RuntimeException exception;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
    }
}
