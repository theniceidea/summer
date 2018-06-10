package com.theniceidea.summer.core.srv;

public abstract class AbsModel {

    private transient Object context;

    public boolean callService(){
        return Manager.callService(this);
    }

    public <T extends AbsModel> T inst(Class<T> cls){
        try {
            T t = cls.newInstance();
            t.setContext(context);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }
}