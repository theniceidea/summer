package com.theniceidea.summer.demo.srv;

import com.theniceidea.summer.base.ExcludeStrategy;
import com.theniceidea.summer.base.ExcludeStrategyClass;
import org.springframework.stereotype.Service;

@Service
@ExcludeStrategyClass
public class ExcludeStrategyService implements ExcludeStrategy{
    @Override
    public boolean isExclude(Class<?> cls, String methodName) {
        return false;
    }
}
