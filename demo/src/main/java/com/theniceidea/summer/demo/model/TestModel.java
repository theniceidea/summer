package com.theniceidea.summer.demo.model;


import com.theniceidea.summer.model.AbsModel;

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
