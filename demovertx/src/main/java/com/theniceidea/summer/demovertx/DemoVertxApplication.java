package com.theniceidea.summer.demovertx;

import com.theniceidea.summer.core.base.EnableSummer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(value = "com.theniceidea.summer.demovertx", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer()
@EnableAspectJAutoProxy
public class DemoVertxApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoVertxApplication.class, args);
    }
}
