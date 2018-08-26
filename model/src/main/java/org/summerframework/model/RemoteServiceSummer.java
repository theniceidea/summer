package org.summerframework.model;

public class RemoteServiceSummer extends Summer {
    public static RemoteServiceSummer Instance(Summer summerSum){
        RemoteServiceSummer remoteServiceSum = new RemoteServiceSummer();
        remoteServiceSum.setSummerSum(summerSum);
        return remoteServiceSum;
    }

    private Summer summerSum;
    private boolean processed;

    public Summer getSummerSum() {
        return summerSum;
    }

    public void setSummerSum(Summer summerSum) {
        this.summerSum = summerSum;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
