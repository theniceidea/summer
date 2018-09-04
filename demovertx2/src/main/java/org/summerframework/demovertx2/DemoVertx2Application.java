package org.summerframework.demovertx2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.summerframework.core.base.EnableSummer;
import org.summerframework.core.srv.ClassRewriter;

import java.io.IOException;

@ComponentScan(value = "org.summerframework.demovertx2", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer
@EnableAspectJAutoProxy
public class DemoVertx2Application {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClassRewriter.rewrite("org");
        SpringApplication.run(DemoVertx2Application.class, args);
    }
}
