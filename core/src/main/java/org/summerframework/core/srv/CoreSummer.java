package org.summerframework.core.srv;

import org.springframework.stereotype.Service;
import org.summerframework.model.Summer;
import org.summerframework.model.SummerService;
import org.summerframework.model.GetRegistedSummerModels;
import org.summerframework.model.SummerServiceBean;

import java.util.Set;

@Service
@SummerService
public class CoreSummer implements SummerServiceBean<GetRegistedSummerModels>{
    @Override
    public void sum(GetRegistedSummerModels summer) {
        Set<Class<?>> classes = Manager
            .localServices
            .keySet();

        summer.setResult(classes);
    }
}
