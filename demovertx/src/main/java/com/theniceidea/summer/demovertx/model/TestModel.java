package com.theniceidea.summer.demovertx.model;

import com.theniceidea.summer.srv.DataModel;

public class TestModel extends DataModel{
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
