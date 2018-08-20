package org.summerframework.demo1;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.summerframework.core.base.EnableSummer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.summerframework.restrpc.EnableRestrpc;

@ComponentScan(value = "org.summerframework.demo1", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer()
@EnableAspectJAutoProxy
@EnableRestrpc
@EnableScheduling
public class Demo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }
}
