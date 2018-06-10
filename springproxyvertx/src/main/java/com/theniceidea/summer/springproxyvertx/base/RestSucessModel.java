package com.theniceidea.summer.springproxyvertx.base;

import com.theniceidea.summer.core.srv.LocalModel;
import com.theniceidea.summer.core.srv.RestfullResultModel;
import com.theniceidea.summer.core.srv.Result;
import io.vertx.ext.web.RoutingContext;

public class RestSucessModel extends RestfullResultModel implements LocalModel {
    private static Object target;

    private RoutingContext routingContext;
    private transient Result<Object> result;

    @Override
    protected Object target() {
        return target;
    }

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public Result<Object> getResult() {
        return result;
    }

    public void setResult(Result<Object> result) {
        this.result = result;
    }
}
