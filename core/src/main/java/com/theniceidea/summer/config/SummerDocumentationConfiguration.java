package com.theniceidea.summer.config;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "com.theniceidea.summer.srv",
    "com.theniceidea.summer.config"
})
public class SummerDocumentationConfiguration {
    public SummerDocumentationConfiguration(){

    }
}
