package org.summerframework.demovertx.base;

import org.summerframework.core.base.SummerService;
import org.summerframework.springproxyvertx.base.RestExceptionModel;
import org.springframework.stereotype.Component;

@Component
@SummerService
public class ExceptionHander {

    @SummerService
    public void summerException(RestExceptionModel model){
    }
}
