package com.theniceidea.autumn.demo;

import com.theniceidea.autumn.base.EnableAutumn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com.theniceidea.autumn.demo", nameGenerator = BeanNameGenerator.class)
@SpringBootApplication
@EnableAutumn
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
