package com.theniceidea.summer.core.srv;

import com.theniceidea.summer.model.SummerSum;

@FunctionalInterface
interface ServiceItem<T extends SummerSum> {
    void callService(T dataModel);
}
