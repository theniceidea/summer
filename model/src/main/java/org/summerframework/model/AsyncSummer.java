package org.summerframework.model;

//import java.lang.reflect.InvocationTargetException;

public abstract class AsyncSummer<R> extends Summer<R>{
    private transient int entryNumber;

    public R sum(){
        throw new RuntimeException("AsyncSummer not support");
    }
    public void sum(AsyncSummer asyncSummer){ }

    public boolean entry(int number){
        return this.entryNumber == number;
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    public AsyncSummer<R> setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
        return this;
    }
}
