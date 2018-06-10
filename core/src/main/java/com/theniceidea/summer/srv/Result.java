package com.theniceidea.summer.srv;

public class Result<T> {
    private transient T value;

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
