package com.theniceidea.summer.core.demo.model;

import com.theniceidea.summer.core.srv.AbsModel;

public class TestModel extends AbsModel {
    private static Object target;
    private String field;

    public static TestModel inst(){
        return new TestModel();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    protected Object target() {
        return target;
    }
}
