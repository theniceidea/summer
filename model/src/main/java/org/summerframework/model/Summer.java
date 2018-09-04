package org.summerframework.model;

public abstract class Summer<R> {
    private transient Object context;
    private transient int entryNumber;
    private transient Summer parent;
    private transient R result;

    public static <T extends Summer> T instance(Class<T> kls){
        try {
            return kls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public R sum(){ return null; }

    public void retun(){
        this.parent.sum();
    }
    public void retun(R result){
        this.setResult(result);
        this.parent.sum();
    }

    public boolean entry(int number){
        return this.entryNumber == number;
    }

    public Summer<R> a(int entryNumber) {
        this.entryNumber = entryNumber;
        return this;
    }

    public <E extends Summer> E b(Class<E> cls){
        return this.instanceWithContext(cls);
    }
    public <E extends Summer> E instanceWithContext(Class<E> cls){
        try {
            Summer t = cls.newInstance();
            t.context = this.context;
            t.parent = this;
            return (E) t;
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

    public int getEntryNumber() {
        return entryNumber;
    }
}
