package com.theniceidea.summer.model;

public abstract class RestfullResultModel<T> extends AbsModel {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
