package org.summerframework.springproxyvertx.base;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "org.summerframework.springproxyvertx.srv",
    "org.summerframework.springproxyvertx.config"
})
class SpringProxyVertxDocumentationConfiguration {
    public SpringProxyVertxDocumentationConfiguration(){

    }
}
