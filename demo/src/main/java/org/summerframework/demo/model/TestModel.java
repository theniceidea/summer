package org.summerframework.demo.model;


import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

public class TestModel extends Summer<String> {
    private static SummerServiceBean<TestModel> service;

    @Override
    public String sum(){
        TestModel summer = new TestModel();
        service.sum(summer);
        return summer.getResult();
    }

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
