package com.theniceidea.summer.demo.model;

import com.theniceidea.summer.srv.DataModel;

public class TestModel extends DataModel{
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
}
