package org.summerframework.springproxyvertx.base;

import org.summerframework.model.Summer;
import org.summerframework.model.LocalSummer;
import io.vertx.ext.web.RoutingContext;

public class RestExceptionModel extends Summer implements LocalSummer {

    private RoutingContext routingContext;
    private Exception exception;

    public RoutingContext getRoutingContext() {
        return routingContext;
    }

    public void setRoutingContext(RoutingContext routingContext) {
        this.routingContext = routingContext;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
