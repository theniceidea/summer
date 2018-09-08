package org.summerframework.model;

public abstract class AsyncSummer<R> extends Summer<R> implements SkipRewrite {
    private transient int summerEntryNumber;
    private transient SceneStack summerStack;
    private transient RuntimeException summerException;

    public static <T, K extends Summer<T>> RootSummer<T> rootSummer(Class<K> kls){
        RootSummer<T> rootSummer = new RootSummer<>();
        Summer<T> tSummer = rootSummer.instanceWithContext(kls);
        rootSummer.setSummer(tSummer);
        return rootSummer;
    }

    protected void reentry(){
        this.sum();
    }
    private void reentryParent(){
        checkParentSummer();
        ((AsyncSummer)this.getSummerParent()).reentry();
    }
    private void checkParentSummer(){
        if(this.getSummerParent() instanceof AsyncSummer) { return; }
        throw new RuntimeException("summerParent is not instanceof AsyncSummer");
    }
    public void retun(){
        reentryParent();
    }
    public void retun(R result){
        this.setSummerResult(result);
        reentryParent();
    }
    public void fireException(Exception re){
        RuntimeException exception;
        if(re instanceof RuntimeException){
            exception = (RuntimeException) re;
        }else{
            exception = new SummerWrapperException(re);
        }
        if(!(this.getSummerParent() instanceof AsyncSummer)) {
            throw new RuntimeException("summerParent is not instanceof AsyncSummer");
        }
        checkParentSummer();
        ((AsyncSummer)this.getSummerParent()).summerException = exception;
        reentryParent();
    }

    public <T extends SceneStack> T recovery(Class<T> cls){
        if(null != this.summerException){
            throw this.summerException;
        }
        if(null == this.summerStack){
            try {
                this.summerStack = cls.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) this.summerStack;
    }

    public int entry() {
        return summerEntryNumber;
    }
    public boolean entryIs(int number){
        return this.summerEntryNumber == number;
    }

    public AsyncSummer<R> a(int entryNumber) {
        this.summerEntryNumber = entryNumber;
        return this;
    }

    public <E extends AsyncSummer> E b(Class<E> cls){
        return this.instanceWithContext(cls);
    }

    public RuntimeException getSummerException() {
        return summerException;
    }
}
