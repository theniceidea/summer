package com.theniceidea.summer.model;

public abstract class SummerSum<R> {
    private transient Object context;
    private transient R result;

    public <T extends SummerSum> T inst(Class<T> cls){
        try {
            T t = cls.newInstance();
            t.setContext(context);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }
}
