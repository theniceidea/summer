package org.summerframework.demovertx.model;


import org.summerframework.model.Summer;

public class TestModel extends Summer<Object> {
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
    public Object getSummerResult() {
        return result;
    }

    public void setSummerResult(Object summerResult) {
        this.result = summerResult;
    }
}
