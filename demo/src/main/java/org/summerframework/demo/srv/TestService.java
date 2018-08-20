package org.summerframework.demo1.srv;

import com.alibaba.fastjson.JSON;
import org.summerframework.model.RemoteServiceSummer;
import org.summerframework.model.SummerService;
import org.summerframework.demo1.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.summerframework.model.GetRegistedSummerModels;

@Service
@SummerService
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

    @SummerService()
    public void task(TestModel model){
        System.out.println(model.getField());
    }
    @SummerService(false)
    public void task(RemoteServiceSummer model){
        System.out.println(JSON.toJSONString(model.getSummerSum()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        TestModel testModel = new TestModel();
        testModel.setField("0000000000000000000000000000");
        testModel.baseSum();
        System.out.println(JSON.toJSONString(testModel));
        GetRegistedSummerModels.sum().forEach(System.out::println);
    }
}
