package org.summerframework.demo2;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * Created by jerry on 16-11-17.
 */
public class BeanNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
//        return super.buildDefaultBeanName(definition);
    }
}
