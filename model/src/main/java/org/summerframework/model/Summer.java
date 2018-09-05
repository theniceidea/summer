package org.summerframework.model;

public abstract class Summer<R> {
    protected transient Object summerContext;
    protected transient Summer summerParent;
    protected transient RuntimeException summerException;
    protected transient R summerResult;

    public static <T extends Summer> T instance(Class<T> kls){
        try {
            return kls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public R sum(){ return null; }

    public <E extends Summer> E instanceWithContext(Class<E> cls){
        try {
            Summer t = cls.newInstance();
            t.summerContext = this.summerContext;
            t.summerParent = this;
            return (E) t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    protected void bindContext(Summer summer){
        summer.summerContext = this.summerContext;
        summer.summerParent = this;
    }

    public R getSummerResult() {
        return summerResult;
    }

    public void setSummerResult(R summerResult) {
        this.summerResult = summerResult;
    }

    public Object getSummerContext() {
        return summerContext;
    }
}
