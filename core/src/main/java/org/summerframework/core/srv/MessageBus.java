package org.summerframework.core.srv;

import org.summerframework.model.AsyncRootSummer;
import org.summerframework.model.Summer;

import java.util.concurrent.ConcurrentSkipListSet;

public final class MessageBus {
    private static ConcurrentSkipListSet<Summer> summers = new ConcurrentSkipListSet<>();

    public static <T extends Summer<T>> AsyncRootSummer<T> rootSummer(Class<Summer<T>> cls){
        try {
            AsyncRootSummer<T> rootSummer = new AsyncRootSummer<T>(cls.newInstance());
            summers.add(rootSummer);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
