package org.summerframework.demo1.model;


import org.summerframework.model.Summer;

import java.io.Serializable;

public class TestModel extends Summer<String> implements Serializable{
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
