package com.theniceidea.summer.srv;

public abstract class AbsModel {
    protected abstract Object target();

    public void callService(){
        Manager.callService(this);
    }
}
