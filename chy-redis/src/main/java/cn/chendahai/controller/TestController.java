package cn.chendahai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/simpleTest")
    public String simpleTest(String key, String value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("simple:" + key, value);
        Object o = valueOperations.get("simple:" + key);
        return "success>>>" + o;
    }
}
