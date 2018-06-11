package com.theniceidea.summer.springproxyvertx.base;

import com.theniceidea.summer.model.LocalModel;
import com.theniceidea.summer.model.RestfullResultModel;
import com.theniceidea.summer.model.Result;
import io.vertx.ext.web.RoutingContext;

public class RestSucessModel extends RestfullResultModel implements LocalModel {

    private RoutingContext routingContext;
    private transient Object result;

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
