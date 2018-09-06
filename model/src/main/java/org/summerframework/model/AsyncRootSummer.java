package org.summerframework.model;

public final class AsyncRootSummer<T> extends AsyncSummer<T>{

    private AsyncSummerResult<T> asyncSummerResult;
    private Summer<T> summer;

    protected AsyncRootSummer(){
    }

    @Override
    protected void reentry() {
        throw new RuntimeException(this.getClass().getName() + "这个类不允许执行reentry");
    }

    @Override
    public T sum(){
        throw new RuntimeException(this.getClass().getName() + "这个类不允许执行sum");
    }

    @Override
    public void retun(T result) {
        this.asyncSummerResult.accept(result, null);
    }

    @Override
    public void fireException(Exception re) {
        this.asyncSummerResult.accept(null, re);
    }

    public Summer<T> getSummer() {
        return summer;
    }

    protected void setSummer(Summer<T> summer) {
        this.summer = summer;
    }
}
