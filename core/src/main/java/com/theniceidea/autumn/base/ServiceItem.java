package com.theniceidea.autumn.base;

import com.theniceidea.autumn.srv.DataModel;

@FunctionalInterface
public interface ServiceItem<T extends DataModel> {
    void callService(T dataModel);
}
