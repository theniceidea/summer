package org.summerframework.demo1.srv;

import org.summerframework.core.base.ExcludeStrategy;
import org.summerframework.model.ExcludeStrategyClass;
import org.springframework.stereotype.Service;

@Service
@ExcludeStrategyClass
public class ExcludeStrategyService implements ExcludeStrategy{
    @Override
    public boolean isExclude(Class<?> cls, String methodName) {
        return false;
    }
}
