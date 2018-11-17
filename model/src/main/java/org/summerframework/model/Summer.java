package org.summerframework.model;

public abstract class Summer<R> implements SkipRewrite {
    private transient Object summerContext;
    private transient Summer summerParent;
    private transient R summerResult;

//    public static <T extends Summer> T instance(Class<T> kls){
//        try {
//            return kls.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public R sum(){ return null; }

    public static  <E extends Summer> E instanceWithContext(Class<E> cls, Object summerContext){
        try {
            Summer t = cls.newInstance();
            t.summerContext = summerContext;
            return (E) t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
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

    public R getSummerResult() {
        return summerResult;
    }

    public void setSummerResult(R summerResult) {
        this.summerResult = summerResult;
    }

    public Object getSummerContext() {
        return summerContext;
    }

    public Summer getSummerParent() {
        return summerParent;
    }
}
