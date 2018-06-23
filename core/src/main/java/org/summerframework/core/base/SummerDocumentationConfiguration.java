package org.summerframework.core.base;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
    "org.summerframework.core.srv",
    "org.summerframework.core.config"
})
class SummerDocumentationConfiguration {
    public SummerDocumentationConfiguration(){

    }
}
