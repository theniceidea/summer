package org.summerframework.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.summerframework.demo1.model.TestModel;
import org.summerframework.restrpcdemomodel.DemoSummer2;

import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("test1")
    public String test1(){
//        return TestModel.instanceWithContext().sum();
        List<String> list = DemoSummer2.sum("1", 10);
        StringBuilder builder = new StringBuilder();
        list.forEach(mod->{
            builder.append(mod);
            builder.append("\n");
        });
        return builder.toString();
    }
}
