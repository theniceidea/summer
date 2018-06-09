package com.theniceidea.summer.base;

import com.theniceidea.summer.srv.DataModel;

@FunctionalInterface
public interface ServiceItem<T extends DataModel> {
    void callService(T dataModel);
}
