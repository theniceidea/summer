package com.theniceidea.summer.demo;

import com.theniceidea.summer.base.EnableSummer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(value = "com.theniceidea.summer.demo", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableSummer
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
