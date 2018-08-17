package org.summerframework.demovertx.base;

import org.summerframework.model.SummerService;
import org.summerframework.springproxyvertx.base.RestSucessModel;
import org.springframework.stereotype.Component;

@Component
@SummerService
public class ResponseBodyWrap {

    @SummerService
    public void bodyWrap(RestSucessModel model){
        Object result = model.getResult();
        model
            .getRoutingContext()
            .response()
            .end(result.toString());

    }
}
