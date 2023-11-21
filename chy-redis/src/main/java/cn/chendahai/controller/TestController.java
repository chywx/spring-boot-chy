package cn.chendahai.controller;

import cn.chendahai.config.ReadFromCustom;
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

//    @Value("${spring.redis.sentinel.nodes}")
//    private List<String> nodes;

//    @Autowired
//    private RedisConfigOld redisConfigOld;

    @GetMapping("/simpleTest")
    public String simpleTest(String key, String value) {
//        System.out.println(nodes);
//        System.out.println(redisConfigOld);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("simple:" + key, value);
        Object o = valueOperations.get("simple:" + key);
        return "success>>>" + o;
    }

    @GetMapping("/simpleTestReadMaster")
    public String simpleTestReadMaster(String key, String value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("simpleTestReadMaster:" + key, value);

        ReadFromCustom.readMaster();
        Object o = valueOperations.get("simpleTestReadMaster:" + key);
        System.out.println(">>>o:" + o);
        ReadFromCustom.clear();


        Object o2 = valueOperations.get("simpleTestReadMaster:" + key);
        System.out.println(">>>o2:" + o2);
        return "success>>>" + o;
    }
}
