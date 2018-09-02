package org.summerframework.demo.srv;

import org.springframework.stereotype.Service;
import org.summerframework.demo.model.TestModel;
import org.summerframework.model.AsyncSummer;
import org.summerframework.model.SummerService;

import java.util.Random;

@Service
@SummerService
public class TestService3 {

    class TestModelCtx extends TestModel{
        public int randomNumber;
        public String userId;
    }
    @SummerService(value = false)
    public void task2(TestModelCtx model){
        if(model.reentry(0)) {
            model.randomNumber = new Random().nextInt(10);
        }else if(model.reentry(100)){
            model.userId = "";
        }else {
            model.setReentryNumber(0);
        }

    }

}
