package com.theniceidea.summer.demovertx.model;

import com.theniceidea.summer.core.srv.RestfullResultModel;
import com.theniceidea.summer.core.srv.Result;

public class TestModel extends RestfullResultModel {
    public static TestModel inst(){
        return new TestModel();
    }

    private Integer eventCode;
    private String field;
    private Result<Object> result;

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
    public Result<Object> getResult() {
        return result;
    }

    public void setResult(Result<Object> result) {
        this.result = result;
    }
}
