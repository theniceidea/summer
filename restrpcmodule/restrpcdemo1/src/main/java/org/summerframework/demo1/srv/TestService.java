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
import org.summerframework.restrpcdemomodel.DemoSummer1;
import org.summerframework.restrpcdemomodel.DemoSummer2;

import java.util.List;

import static org.summerframework.core.srv.Summers.sum;

@Service
@SummerService
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

    @SummerService()
    public void task(TestModel model){
        model.setResult("hahahiiiiii");
    }
    @SummerService(false)
    public void task(RemoteServiceSummer model){
        System.out.println(JSON.toJSONString(model.getSummerSum()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        TestModel testModel = new TestModel();
//        testModel.setField("0000000000000000000000000000");
//        sum(testModel);
//        System.out.println(JSON.toJSONString(testModel));
//        GetRegistedSummerModels.sum().forEach(System.out::println);
//        Boolean result = DemoSummer1.sum("1", 10);
//        System.out.println("DemoSummer1===========================result:"+result);


    }
}
