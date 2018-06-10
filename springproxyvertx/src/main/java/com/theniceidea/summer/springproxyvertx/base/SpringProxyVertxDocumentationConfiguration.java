package com.theniceidea.summer.springproxyvertx.base;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "com.theniceidea.summer.springproxyvertx.srv",
    "com.theniceidea.summer.springproxyvertx.config"
})
class SpringProxyVertxDocumentationConfiguration {
    public SpringProxyVertxDocumentationConfiguration(){

    }
}
