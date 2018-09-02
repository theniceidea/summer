package org.summerframework.model;

//import java.lang.reflect.InvocationTargetException;

public abstract class AsyncSummer<R> extends Summer<R>{
    private transient int reentryNumber;

    public R sum(){
        throw new RuntimeException("AsyncSummer not support");
    }
    public void sum(AsyncSummer asyncSummer){ }

    public boolean reentry(int number){
        return this.reentryNumber == number;
    }

    public int getReentryNumber() {
        return reentryNumber;
    }

    public AsyncSummer<R> setReentryNumber(int reentryNumber) {
        this.reentryNumber = reentryNumber;
        return this;
    }
}
