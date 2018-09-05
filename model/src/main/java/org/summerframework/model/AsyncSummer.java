package org.summerframework.model;

public abstract class AsyncSummer<R> extends Summer<R> {
    protected transient int summerEntryNumber;
    protected transient SceneStack summerStack;
    protected transient AsyncSummerResult<R> asyncSummerResult;

    protected void reentry(){
        this.sum();
    }
    private void reentryParent(){
        if(this.summerParent instanceof AsyncSummer) {
            ((AsyncSummer)this.summerParent).reentry();
        }
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
        if(null == this.summerParent) throw exception;
        this.summerParent.summerException = exception;
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

    public boolean entry(int number){
        return this.summerEntryNumber == number;
    }

    public AsyncSummer<R> a(int entryNumber) {
        this.summerEntryNumber = entryNumber;
        return this;
    }

    public <E extends AsyncSummer> E b(Class<E> cls){
        return this.instanceWithContext(cls);
    }
    protected void bindContext(AsyncSummer summer){
        summer.summerContext = this.summerContext;
        summer.summerParent = this;
    }

    public int getSummerEntryNumber() {
        return summerEntryNumber;
    }
}
