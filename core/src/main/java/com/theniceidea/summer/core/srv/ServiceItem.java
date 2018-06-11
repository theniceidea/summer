package com.theniceidea.summer.core.srv;

import com.theniceidea.summer.model.AbsModel;

@FunctionalInterface
interface ServiceItem<T extends AbsModel> {
    void callService(T dataModel);
}
