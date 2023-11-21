package cn.chendahai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/multi")
public class TestMultiController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/rollbackSessionCallback")
    public String rollbackSessionCallback() {

//        redisTemplate.multi();
        /*
        Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed;
        nested exception is org.springframework.data.redis.RedisSystemException: Error in execution;
        nested exception is io.lettuce.core.RedisCommandExecutionException: ERR EXEC without MULTI] with root cause
         */
//        List exec = redisTemplate.exec();
//        System.out.println(exec);
        Object sessionCallback = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                operations.multi();
                Map<String, Object> map = new HashMap<>();
                map.put("a", "aaa");
                map.put("b", 222);
//                operations.opsForValue().set("multi:k1", "v1");
                operations.opsForValue().set("multi:k3", map);
                Object o = operations.opsForValue().get("multi:k3");

                List exec = operations.exec();
                return exec;
            }
        });

        System.out.println(sessionCallback);
        System.out.println("SessionCallback end");


        return "rollbackSessionCallback success";
    }


    @GetMapping("/rollbackRedisCallback")
    public String rollbackRedisCallback() {

        Object redisCallback = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

//                for (RedisClientInfo redisClientInfo : connection.getClientList()) {
//                    System.out.println(redisClientInfo);
//                }

                String key2 = "multi:k2";
                connection.watch(serializer.serialize(key2));
                connection.watch(serializer.serialize(key2));
                connection.multi();


                Object name = redisTemplate.execute((RedisCallback<String>) connection2 -> {
                    RedisSerializer<String> serializer2 = redisTemplate.getStringSerializer();
                    byte[] value = connection2.hGet(serializer2.serialize("hash:person"), serializer2.serialize("name"));
                    return serializer.deserialize(value);
                });
                System.out.println("name:" + name);


//                try {
//                    Thread.sleep(61000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }


                for (int i = 0; i < 1000; i++) {
                    connection.set(serializer.serialize(key2), serializer.serialize("v2:" + System.currentTimeMillis()));
                    byte[] bytes = connection.get(serializer.serialize(key2));
                }

                List<Object> exec = connection.exec();
                return exec;
            }
        });

        System.out.println("redisCallback:" + redisCallback);
        System.out.println("RedisCallback end");


        return "rollbackRedisCallback success";
    }

}
