package com.theniceidea.summer.core.base;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "com.theniceidea.summer.core.srv",
    "com.theniceidea.summer.core.config"
})
class SummerDocumentationConfiguration {
    public SummerDocumentationConfiguration(){

    }
}
