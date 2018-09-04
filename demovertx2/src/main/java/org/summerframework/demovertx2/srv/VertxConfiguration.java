package org.summerframework.demovertx2.srv;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfiguration {

    private Vertx vertx;

    @Value("${jerry.redis.host}")
    private String redisHost;

    @Value("${jerry.redis.auth}")
    private String redisAuth;

    @Bean
    public RedisClient redisClient() {
        RedisOptions config = new RedisOptions()
            .setHost(redisHost)
            .setAuth(redisAuth);

        return RedisClient.create(vertx(), config);
    }

    @Bean
    public Vertx vertx() {
        if (null == vertx) {
            vertx = Vertx.vertx();
        }
        return vertx;
    }

    @Bean
    public Router route() {
        Router router = Router.router(vertx());
        router.route()
            .handler(BodyHandler.create());
        return router;
    }
}
