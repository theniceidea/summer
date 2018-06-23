package org.summerframework.demovertx.config;

import org.summerframework.demovertx.DemoVertxApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage(DemoVertxApplication.class.getPackage().getName()))
            .paths(PathSelectors.any())
            .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("cms api")
            .description("cms description.\n" +
                             "大部分情况下接口的返回数据类型是json.\n" +
                             "格式为: {code: '0', msg: '', result: '根据结果不同,result返回的类型不同,参考各接口返回值'}.\n" +
                             "其中code为0表示接口调用成功,注意code是字符串类型,其它值都为失败,失败会有msg消息.\n")
            .termsOfServiceUrl("http://blog.csdn.net/forezp")
            .version("1.0")
            .build();
    }

}
