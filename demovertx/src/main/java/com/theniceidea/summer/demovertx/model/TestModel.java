package com.theniceidea.summer.demovertx.model;

import com.theniceidea.summer.core.srv.AbsModel;

public class TestModel extends AbsModel {
    public static TestModel inst(){
        return new TestModel();
    }

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
