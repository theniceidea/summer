package org.summerframework.springproxyvertx.base;

import org.summerframework.model.SummerSum;
import org.summerframework.model.LocalSum;
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
