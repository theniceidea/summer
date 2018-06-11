package com.theniceidea.summer.springproxyvertx.srv;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfiguration {

    private Vertx vertx;

    @Bean
    public Vertx vertx(){
        if(null == vertx) vertx = Vertx.vertx();
        return vertx;
    }
    @Bean
    public Router route(){
        Router router = Router.router(vertx());
        router.route().handler(BodyHandler.create());
        return router;
    }
}
