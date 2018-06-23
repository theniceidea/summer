package org.summerframework.demovertx;

import org.summerframework.core.base.EnableSummer;
import org.summerframework.springproxyvertx.base.EnableSpringProxyVertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(value = "org.summerframework.demovertx", nameGenerator = BeanNameGenerator.class)
@EnableAspectJAutoProxy
@EnableSummer()
@EnableSpringProxyVertx
public class DemoVertxApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoVertxApplication.class, args);
    }
}
