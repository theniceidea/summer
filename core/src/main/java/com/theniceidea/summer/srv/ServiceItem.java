package com.theniceidea.summer.srv;

@FunctionalInterface
interface ServiceItem<T extends AbsModel> {
    void callService(T dataModel);
}
