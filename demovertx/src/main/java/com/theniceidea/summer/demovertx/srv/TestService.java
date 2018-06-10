package com.theniceidea.summer.demovertx.srv;

import com.alibaba.fastjson.JSON;
import com.theniceidea.summer.base.SummerService;
import com.theniceidea.summer.base.SummerServiceClass;
import com.theniceidea.summer.demovertx.model.TestModel;
import jdk.nashorn.internal.parser.JSONParser;
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
        testModel.exec();
        System.out.println(JSON.toJSONString(testModel));
    }
}
