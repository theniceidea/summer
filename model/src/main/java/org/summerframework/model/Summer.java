package org.summerframework.model;

public abstract class Summer<R> {
    private transient Object summerContext;
    private transient int summerEntryNumber;
    private transient Summer summerParent;
    private transient AsyncSummerStack summerStack;
    private transient R summerResult;

    public static <T extends Summer> T instance(Class<T> kls){
        try {
            return kls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public R sum(){ return null; }

    public void retun(){
        this.summerParent.sum();
    }
    public void retun(R result){
        this.setSummerResult(result);
        this.summerParent.sum();
    }

    public <T extends AsyncSummerStack> T stack(Class<T> cls){
        if(null == this.summerStack){
            try {
                this.summerStack = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) this.summerStack;
    }
    public boolean exception(){

    }

    public boolean entry(int number){
        return this.summerEntryNumber == number;
    }

    public Summer<R> a(int entryNumber) {
        this.summerEntryNumber = entryNumber;
        return this;
    }

    public <E extends Summer> E b(Class<E> cls){
        return this.instanceWithContext(cls);
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

    public int getSummerEntryNumber() {
        return summerEntryNumber;
    }
}
