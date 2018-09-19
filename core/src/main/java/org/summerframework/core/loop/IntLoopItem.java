package org.summerframework.core.loop;

public final class IntLoopItem {
    private int i;
    private int max;
    private IntLoopConsumer consumer;

    public IntLoopItem(int i, int max, IntLoopConsumer consumer){
        this.i = i;
        this.max = max;
        this.consumer = consumer;
    }

    public int getI() {
        return i;
    }

    public int getMax() {
        return max;
    }

    public IntLoopConsumer getConsumer() {
        return consumer;
    }
}
