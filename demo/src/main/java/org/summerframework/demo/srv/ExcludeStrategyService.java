package org.summerframework.demo.srv;

import org.summerframework.core.base.ExcludeStrategy;
import org.summerframework.core.base.ExcludeStrategyClass;
import org.springframework.stereotype.Service;

@Service
@ExcludeStrategyClass
public class ExcludeStrategyService implements ExcludeStrategy{
    @Override
    public boolean isExclude(Class<?> cls, String methodName) {
        return false;
    }
}
