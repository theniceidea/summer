package com.theniceidea.summer.demovertx;

import com.theniceidea.summer.core.base.EnableSummer;
import com.theniceidea.summer.springproxyvertx.base.EnableSpringProxyVertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(value = "com.theniceidea.summer.demovertx", nameGenerator = BeanNameGenerator.class)
@EnableAspectJAutoProxy
@EnableSummer()
@EnableSpringProxyVertx
public class DemoVertxApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoVertxApplication.class, args);
    }
}
