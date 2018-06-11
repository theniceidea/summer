package com.theniceidea.summer.springproxyvertx.base;

import com.theniceidea.summer.model.SummerSum;
import com.theniceidea.summer.model.LocalSum;
import io.vertx.ext.web.RoutingContext;

public class RestSucessModel extends SummerSum implements LocalSum {

    private RoutingContext routingContext;

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

}
