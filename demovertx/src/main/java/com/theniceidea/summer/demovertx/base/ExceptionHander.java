package com.theniceidea.summer.demovertx.base;

import com.theniceidea.summer.core.base.SummerService;
import com.theniceidea.summer.core.base.SummerServiceClass;
import com.theniceidea.summer.springproxyvertx.base.RestExceptionModel;
import org.springframework.stereotype.Component;

@Component
@SummerServiceClass
public class ExceptionHander {

    @SummerService
    public void summerException(RestExceptionModel model){
    }
}
