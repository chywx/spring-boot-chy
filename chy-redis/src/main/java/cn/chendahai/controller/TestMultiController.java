package cn.chendahai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/multi")
public class TestMultiController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/rollback")
    public String rollback() {

        redisTemplate.opsForValue().set("k1", "v1");
        System.out.println("kv end");

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

        Object redisCallback = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                connection.multi();

                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize("multi:k2"), serializer.serialize("v2"));
                byte[] bytes = connection.get(serializer.serialize("multi:k2"));


                List<Object> exec = connection.exec();
                return exec;
            }
        });
        System.out.println(redisCallback);
//
        System.out.println("RedisCallback end");

        return "rollback success";
    }

}
