package org.summerframework.core.srv;

import org.summerframework.model.Summer;

@FunctionalInterface
interface ServiceItem<T extends Summer> {
    void callService(T dataModel);
}
