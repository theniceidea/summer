package org.summerframework.springproxyvertx.base;

import org.summerframework.model.SummerSum;
import org.summerframework.model.LocalSum;
import org.summerframework.model.OptionalSum;
import io.vertx.ext.web.RoutingContext;

public class RestExceptionModel extends SummerSum implements LocalSum, OptionalSum {

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
