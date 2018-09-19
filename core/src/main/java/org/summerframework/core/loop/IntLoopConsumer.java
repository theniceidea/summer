package org.summerframework.core.loop;

@FunctionalInterface
public interface IntLoopConsumer {
    void accept(int i, int max);
}
