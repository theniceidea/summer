package com.theniceidea.summer.demo.srv;

import com.theniceidea.summer.demo.model.TestModel;
import com.theniceidea.summer.srv.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ApplicationRunner{

    @Autowired
    private TestService testService;

    public void task(TestModel model){
        System.out.println(model.getField());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Manager.registeService(TestModel.class, testService::task);
    }
}
