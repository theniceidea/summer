package org.summerframework.core.srv;

import org.springframework.stereotype.Service;
import org.summerframework.core.base.SummerService;
import org.summerframework.model.GetRegistedSummerModels;

import java.util.Set;

@Service
@SummerService
public class CoreSummer {
    public void getRegistedSummerModels(GetRegistedSummerModels model){
        Set<Class<?>> classes = Manager
            .localServices
            .keySet();

        model.setResult(classes);
    }
}
