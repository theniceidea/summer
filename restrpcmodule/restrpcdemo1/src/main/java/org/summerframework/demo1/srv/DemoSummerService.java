package org.summerframework.demo1.srv;

import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcdemomodel.DemoSummer3;

@Service
@SummerService
public class DemoSummerService {
    public void demoSummer(DemoSummer3 mod){
        mod.setSummerResult("demoSummer3");
    }
}
