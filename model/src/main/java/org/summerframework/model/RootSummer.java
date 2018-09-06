package org.summerframework.model;

import java.util.function.Consumer;

public final class RootSummer<T> extends AsyncSummer<T>{

    private Consumer<T> consumer;
    private Summer<T> summer;

    protected RootSummer(){
    }

    @Override
    protected void reentry() {
        this.consumer.accept(this.summer.getSummerResult());
    }
    public void sum(Consumer<T> consumer){
        this.consumer = consumer;
        this.summer.sum();
    }

    @Override
    public T sum(){
        throw new RuntimeException(this.getClass().getName() + "这个类不允许执行sum");
    }

    @Override
    public void retun(T result) {
        throw new RuntimeException(this.getClass().getName() + "这个类不允许执行retun");
    }

    @Override
    public void fireException(Exception re) {
        RuntimeException exception;
        if(re instanceof RuntimeException){
            exception = (RuntimeException) re;
        }else{
            exception = new SummerWrapperException(re);
        }
        throw exception;
    }

    public Summer<T> getSummer() {
        return summer;
    }

    protected void setSummer(Summer<T> summer) {
        this.summer = summer;
    }
}
