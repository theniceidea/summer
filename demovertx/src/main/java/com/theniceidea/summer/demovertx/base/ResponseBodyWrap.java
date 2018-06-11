package com.theniceidea.summer.demovertx.base;

import com.theniceidea.summer.core.base.SummerService;
import com.theniceidea.summer.core.base.SummerServiceClass;
import com.theniceidea.summer.core.srv.RestfullResultModel;
import com.theniceidea.summer.core.srv.Result;
import com.theniceidea.summer.springproxyvertx.base.RestSucessModel;
import org.springframework.stereotype.Component;

@Component
@SummerServiceClass
public class ResponseBodyWrap {

    @SummerService
    public void bodyWrap(RestSucessModel model){
        Result<Object> result = model.getResult();
        model
            .getRoutingContext()
            .response()
            .end(result.getValue().toString());

    }
}
