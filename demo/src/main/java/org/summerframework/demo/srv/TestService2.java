package org.summerframework.demo.srv;

import com.alibaba.fastjson.JSON;
import org.summerframework.core.base.SummerService;
import org.summerframework.demo.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import static org.summerframework.core.srv.Summers.sum;

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
