package org.summerframework.core.srv;

import org.summerframework.model.SummerSum;

@FunctionalInterface
interface ServiceItem<T extends SummerSum> {
    void callService(T dataModel);
}
