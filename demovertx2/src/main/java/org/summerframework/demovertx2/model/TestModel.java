package org.summerframework.demovertx2.model;


import org.summerframework.model.AsyncSummer;
import org.summerframework.model.Summer;

import java.util.function.Consumer;

public class TestModel extends AsyncSummer<String> {

    private String field;

    public void task(Consumer consumer){

    }
    public void loop(Consumer consumer){

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
