package org.summerframework.demo2.srv;

import org.springframework.stereotype.Service;
import org.summerframework.model.SummerService;
import org.summerframework.restrpcdemomodel.DemoSummer1;
import org.summerframework.restrpcdemomodel.DemoSummer2;
import org.summerframework.restrpcdemomodel.DemoSummer3;

import java.util.ArrayList;
import java.util.List;

@Service
@SummerService
public class DemoSummerService {
    public void demoSummer(DemoSummer1 mod){
        mod.setResult(true);
    }
    public void demoSummer(DemoSummer2 mod){
        List<String> list = new ArrayList<>();

        for(int i=0; i<10; i++){
            list.add(mod.getField1()+mod.getField2()+"hahaha");
        }
        String ret = DemoSummer3.sum("n", 2);
        list.add(ret);
        mod.setResult(list);

    }
}
