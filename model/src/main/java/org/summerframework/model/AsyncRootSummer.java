package org.summerframework.model;

public class AsyncRootSummer<T> extends AsyncSummer<T>{

    private AsyncSummerResult<T> asyncSummerResult;
    private Summer<T> summer;

    public AsyncRootSummer(Summer<T> summer){
        this.summer = summer;
        this.bindContext(summer);
    }

    @Override
    protected void reentry() {
        this.asyncSummerResult.accept(this.summer.getSummerResult(), null);
    }

    public void sum(AsyncSummerResult<T> result){
        this.asyncSummerResult = result;
        this.summer.sum();
    }

    @Override
    public void retun(T result) {
        this.asyncSummerResult.accept(result, null);
    }

    @Override
    public void fireException(Exception re) {
        this.asyncSummerResult.accept(null, re);
    }
}
