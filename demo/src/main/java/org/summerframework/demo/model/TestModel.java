package org.summerframework.demo.model;


import org.summerframework.model.AsyncSummer;

public class TestModel extends AsyncSummer<String> {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
