package org.summerframework.demovertx2.srv;

import io.vertx.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;
import org.summerframework.demovertx2.model.RedisGet;
import org.summerframework.demovertx2.model.TestModel;
import org.summerframework.model.SceneStack;
import org.summerframework.model.SummerService;
import org.summerframework.model.SkipReturnCheck;

@Service
@SummerService
public class TestService4 extends SceneStack{
    private static String redisKey = "wechat://accounts://wx82f2d8729b753f35";

    @Autowired
    private RedisClient redisClient;
    private RedisGet redisGet;

    @SkipReturnCheck
    public void redisGet(RedisGet m){
        //create Hander
//        redisClient.get(m.getKey(), r -> m.retun(r.succeeded() ? r.result() : null));
        redisClient.get(m.getKey(), r -> m.fireException(new RuntimeException("uuuuuuuuuu")));
    }
//    @SummerService(false)
    public void task2(TestModel model){
        TestService4 stack = model.recovery(TestService4.class);
        if (model.entry(0)) {
            stack.redisGet = model.a(100).b(RedisGet.class).c(redisKey);
        }else if (model.entry(100)) {
            model.retun(stack.redisGet.getSummerResult());
        }
    }

}
