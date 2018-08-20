package org.summerframework.restrpcmodel;

import org.summerframework.model.Summer;

public class ReceiveRestrpcSummer extends Summer<Void> {

    private Object request;
    private Object response;

    public static void sum(Object request, Object response){
        ReceiveRestrpcSummer summer = new ReceiveRestrpcSummer();
        summer.setRequest(request);
        summer.setResponse(response);
        summer.baseSum();
    }

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

