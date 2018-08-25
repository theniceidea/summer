package org.summerframework.demo2;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.summerframework.core.base.EnableSummer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.summerframework.core.srv.ClassRewriter;
import org.summerframework.restrpc.EnableRestrpc;

@ComponentScan(value = "org.summerframework.demo2", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer()
@EnableAspectJAutoProxy
@EnableRestrpc
@EnableScheduling
public class Demo2Application {
    public static void main(String[] args) {
        ClassRewriter.rewrite("org");
        SpringApplication.run(Demo2Application.class, args);
    }
}
