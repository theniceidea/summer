package com.theniceidea.summer.demo.model;


import com.theniceidea.summer.model.SummerSum;

public class TestModel extends SummerSum<String> {
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
