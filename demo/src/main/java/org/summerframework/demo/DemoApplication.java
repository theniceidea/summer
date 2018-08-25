package org.summerframework.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.summerframework.core.base.EnableSummer;
import org.summerframework.core.srv.ClassRewriter;
import org.summerframework.demo.model.RewriteTest;

import java.io.IOException;

@ComponentScan(value = "org.summerframework.demo", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer
@EnableAspectJAutoProxy
public class DemoApplication {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        List<String> classList = ClassUtil.getClasses("org");
//        classList.forEach(System.out::println);
        new ClassRewriter().rewrite("org");
        new RewriteTest().test();
//        SpringApplication.run(DemoApplication.class, args);
    }
}
