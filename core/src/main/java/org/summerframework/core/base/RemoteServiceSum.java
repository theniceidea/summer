package org.summerframework.core.base;

import org.summerframework.model.SummerSum;

public class RemoteServiceSum extends SummerSum {
    public static RemoteServiceSum New(SummerSum summerSum){
        RemoteServiceSum remoteServiceSum = new RemoteServiceSum();
        remoteServiceSum.setSummerSum(summerSum);
        return remoteServiceSum;
    }

    private SummerSum summerSum;
    private boolean processed;

    public SummerSum getSummerSum() {
        return summerSum;
    }

    public void setSummerSum(SummerSum summerSum) {
        this.summerSum = summerSum;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
