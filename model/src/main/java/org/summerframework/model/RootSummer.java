package org.summerframework.model;

import java.util.function.Consumer;

public final class RootSummer<T> extends AsyncSummer<T> implements SkipRewrite{

    private Consumer<AsyncSummerResult<T>> consumer;
    private Summer<T> summer;

    protected RootSummer(){
    }

    @Override
    protected void reentry() {
        AsyncSummerResult<T> result = new AsyncSummerResult<>();
        result.setResult(this.summer.getSummerResult());
        result.setException(this.getSummerException());

        this.consumer.accept(result);
    }
    public void sum(Consumer<AsyncSummerResult<T>> consumer){
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
