package org.summerframework.restrpcdemomodel;

import org.summerframework.model.Summer;

import java.io.Serializable;

public class DemoSummer1 extends Summer<Boolean> implements Serializable{
    private String field1;
    private int field2;

    public static Boolean sum(String field1, int field2){
        DemoSummer1 summer = new DemoSummer1();

        summer.setField1(field1);
        summer.setField2(field2);

        return summer.baseSum();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }
}
