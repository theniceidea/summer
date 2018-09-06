package org.summerframework.model;

public abstract class Summer<R> {
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

    public <E extends Summer> E instanceWithContext(Class<E> cls){
        try {
            Summer t = cls.newInstance();
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

    public Summer getSummerParent() {
        return summerParent;
    }
}
