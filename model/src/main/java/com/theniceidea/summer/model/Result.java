package com.theniceidea.summer.model;

public class Result<T> {
    private transient T value;
    public static <T> Result<T> NewResult(T value){
        return new Result<>(value);
    }

    public Result(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
