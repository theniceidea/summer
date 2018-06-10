package com.theniceidea.summer.srv;

public abstract class DataModel {
    protected abstract Object target();

    public void exec(){
        Manager.execService(this);
    }
}
