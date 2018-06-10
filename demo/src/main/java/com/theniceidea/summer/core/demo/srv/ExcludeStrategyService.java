package com.theniceidea.summer.core.demo.srv;

import com.theniceidea.summer.core.base.ExcludeStrategy;
import com.theniceidea.summer.core.base.ExcludeStrategyClass;
import org.springframework.stereotype.Service;

@Service
@ExcludeStrategyClass
public class ExcludeStrategyService implements ExcludeStrategy{
    @Override
    public boolean isExclude(Class<?> cls, String methodName) {
        return false;
    }
}
