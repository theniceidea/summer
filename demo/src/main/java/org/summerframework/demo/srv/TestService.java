package org.summerframework.demo.srv;

import com.alibaba.fastjson.JSON;
import org.summerframework.model.RemoteServiceSum;
import org.summerframework.model.SummerService;
import org.summerframework.demo.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.summerframework.model.GetRegistedSummerModels;

import static org.summerframework.core.srv.Summers.sum;

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
    public void task(RemoteServiceSum model){
        System.out.println(JSON.toJSONString(model.getSummerSum()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        TestModel testModel = new TestModel();
        testModel.setField("0000000000000000000000000000");
        sum(testModel);
        System.out.println(JSON.toJSONString(testModel));
        GetRegistedSummerModels.sum().forEach(System.out::println);
    }
}
