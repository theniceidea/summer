package org.summerframework.demovertx2.model;


import org.summerframework.model.AsyncSummer;
import org.summerframework.model.Summer;

public class TestModel extends AsyncSummer<String> {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
