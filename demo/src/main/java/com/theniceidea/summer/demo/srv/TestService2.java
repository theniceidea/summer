package com.theniceidea.summer.demo.srv;

import com.alibaba.fastjson.JSON;
import com.theniceidea.summer.core.base.SummerService;
import com.theniceidea.summer.demo.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import static com.theniceidea.summer.core.srv.Summers.sum;

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
