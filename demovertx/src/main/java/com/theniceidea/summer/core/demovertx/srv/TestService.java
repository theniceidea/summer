package com.theniceidea.summer.core.demovertx.srv;

import com.alibaba.fastjson.JSON;
import com.theniceidea.summer.core.base.SummerService;
import com.theniceidea.summer.core.base.SummerServiceClass;
import com.theniceidea.summer.core.demovertx.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@SummerServiceClass
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

    @SummerService
    public void task(TestModel model){
        System.out.println(model.getField());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TestModel testModel = new TestModel();
        testModel.setField("0000000000000000000000000000");
        testModel.callService();
        System.out.println(JSON.toJSONString(testModel));
    }
}
