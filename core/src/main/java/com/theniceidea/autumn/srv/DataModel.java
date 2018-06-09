package com.theniceidea.autumn.srv;

public abstract class DataModel {
    private String serviceName;
    public DataModel(){
        this.setServiceName(this.getClass().getName());
    }

    public void exec(){
        Manager.execService(this);
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
