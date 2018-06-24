package org.summerframework.core.base;

public class RemoteServiceItem {
    private String key;
    private String addr;

    private static RemoteServiceItem New(
        String key,
        String addr
    ){
        RemoteServiceItem model = new RemoteServiceItem();
        model.key = key;
        model.addr = addr;
        return model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
