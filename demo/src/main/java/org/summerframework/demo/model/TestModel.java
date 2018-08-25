package org.summerframework.demo.model;


import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

public class TestModel extends Summer<String> {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
