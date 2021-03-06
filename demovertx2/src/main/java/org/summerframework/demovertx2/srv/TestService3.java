package org.summerframework.demovertx2.srv;

import io.vertx.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summerframework.demovertx2.model.RedisGet;
import org.summerframework.demovertx2.model.TestModel;
import org.summerframework.model.SceneStack;
import org.summerframework.model.SummerService;

@Service
@SummerService(false)
public class TestService3 {
    private String redisKey = "wechat://accounts://wx82f2d8729b753f35";

    @Autowired
    private RedisClient redisClient;

    public static class TestModelStack extends SceneStack {
        public int randomNumber;
        public String userId;
        public RedisGet redisGet;
    }
    public void redisGet(RedisGet m){
        //create Hander
        redisClient.get(m.getKey(), r -> m.retun(r.succeeded() ? r.result() : null));
    }
    @SummerService(value = false)
    public void task2(TestModel model, TestModelStack stack){
        try {
            stack = model.recovery(TestModelStack.class);
            if (model.entry(0)) {
                stack.redisGet = model.a(100).b(RedisGet.class).c(redisKey);
            } else if (model.entry(100)) {
                System.out.println("===================================");
                System.out.println(stack.redisGet.getSummerResult());
                System.out.println("===================================");
            }
        }finally {

        }

    }

}
