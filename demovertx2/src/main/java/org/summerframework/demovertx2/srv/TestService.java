package org.summerframework.demovertx2.srv;

import com.alibaba.fastjson.JSON;
import org.summerframework.demovertx2.model.TestModel;
import org.summerframework.model.RemoteServiceSummer;
import org.summerframework.model.SummerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@SummerService
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

    @SummerService(false)
    public void task(TestModel model){
        System.out.println("0000000000000000");
        System.out.println(model.getField());
    }
    @SummerService(false)
    public void task(RemoteServiceSummer model){
        System.out.println(JSON.toJSONString(model.getSummerSum()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        TestModel testModel = Summer.instance(TestModel.class);
//        testModel.setField("0000000000000000000000000000");
//        testModel.sum();
//        System.out.println(JSON.toJSONString(testModel));
//        GetRegistedSummerModels.sum().forEach(System.out::println);

        TestModel testModel = new TestModel();
        testModel.sum();
    }
}
