package org.summerframework.demovertx.model;


import org.summerframework.model.Summer;
import org.summerframework.model.SummerServiceBean;

public class TestModel extends Summer<Object> {
    private static SummerServiceBean<TestModel> service;
    public static Object sum(){
        TestModel summer = new TestModel();
        service.sum(summer);
        return summer.getResult();
    }
    private Integer eventCode;
    private String field;
    private Object result;

    public Integer getEventCode() {
        return eventCode;
    }

    public void setEventCode(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
