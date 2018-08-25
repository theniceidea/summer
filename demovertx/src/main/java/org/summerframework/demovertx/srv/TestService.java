package org.summerframework.demovertx.srv;

import com.alibaba.fastjson.JSON;
import org.summerframework.model.SummerService;
import org.summerframework.demovertx.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@SummerService()
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

//    @SummerService(value = false)
    public void task(TestModel model){
        System.out.println(model.getField());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TestModel testModel = new TestModel();
        testModel.setField("0000000000000000000000000000");
        testModel.sum();
        System.out.println(JSON.toJSONString(testModel));
    }
}
