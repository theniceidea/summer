package org.summerframework.model;

public class SummerWrapperException extends RuntimeException{
    public SummerWrapperException() {
        super();
    }

    public SummerWrapperException(String message) {
        super(message);
    }

    public SummerWrapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public SummerWrapperException(Throwable cause) {
        super(cause);
    }

    protected SummerWrapperException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
