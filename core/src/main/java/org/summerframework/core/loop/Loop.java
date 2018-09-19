package org.summerframework.core.loop;

public final class Loop {
    public static Loop loop(int i, int max, IntLoopConsumer consumer){
        for(int index=i; index<=max; index++){
            loop2(index, max, (i1, max1) -> {
                consumer.accept(i1, max1);
            });
        }
    }
    private static void loop2(int i, int max, IntLoopConsumer consumer){
        consumer.accept(i, max);
    }
    private static void aaa(){
        Loop.loop(0, 100, (i, max) -> {

        });
    }
}
