package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

public class ReceiveRestrpcSummer extends Summer<Void> {

    private Object request;
    private Object response;

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}

