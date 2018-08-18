package org.summerframework.springproxyvertx.base;

import org.summerframework.model.Summer;
import org.summerframework.model.LocalSummer;
import io.vertx.ext.web.RoutingContext;

public class RestSucessModel extends Summer implements LocalSummer {

    private RoutingContext routingContext;

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

}
