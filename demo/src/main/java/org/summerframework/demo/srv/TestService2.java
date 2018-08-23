package org.summerframework.demo1.srv;

import org.summerframework.demo.model.TestModel;
import org.summerframework.model.SummerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SummerService
public class TestService2 {

    @Autowired
    private TestService2 testService;

    @SummerService(value = false)
    public void task2(TestModel model){
        System.out.println(model.getField());
    }

}
