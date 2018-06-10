package com.theniceidea.summer.srv;

import com.theniceidea.summer.srv.DataModel;

@FunctionalInterface
interface ServiceItem<T extends DataModel> {
    void callService(T dataModel);
}
