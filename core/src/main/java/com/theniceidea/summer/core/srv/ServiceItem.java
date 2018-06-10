package com.theniceidea.summer.core.srv;

@FunctionalInterface
interface ServiceItem<T extends AbsModel> {
    void callService(T dataModel);
}
