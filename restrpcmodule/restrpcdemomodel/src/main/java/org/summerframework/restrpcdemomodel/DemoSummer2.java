package org.summerframework.restrpcdemomodel;

import org.summerframework.model.Summer;

import java.io.Serializable;
import java.util.List;

public class DemoSummer2 extends Summer<List<String>> implements Serializable{
    private String field1;
    private int field2;

    public static List<String> sum(String field1, int field2){
        DemoSummer2 summer = Summer.instance(DemoSummer2.class);

        summer.setField1(field1);
        summer.setField2(field2);

        return summer.sum();
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
