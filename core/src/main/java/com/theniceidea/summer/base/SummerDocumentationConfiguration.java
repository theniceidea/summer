package com.theniceidea.summer.base;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "com.theniceidea.summer.srv",
    "com.theniceidea.summer.config"
})
class SummerDocumentationConfiguration {
    public SummerDocumentationConfiguration(){

    }
}
