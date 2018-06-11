package com.theniceidea.summer.demovertx.base;

import com.theniceidea.summer.core.base.SummerService;
import com.theniceidea.summer.core.base.SummerServiceClass;
import com.theniceidea.summer.springproxyvertx.base.RestSucessModel;
import org.springframework.stereotype.Component;

@Component
@SummerServiceClass
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
