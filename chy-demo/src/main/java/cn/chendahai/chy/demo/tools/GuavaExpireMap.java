package cn.chendahai.chy.demo.tools;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.TimeUnit;

public class GuavaExpireMap {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> map = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
//                .maximumSize(2)
                .build();

        map.put("k1", "v1");
        map.put("k2", "v1");
        map.put("k3", "v1");
        String k1 = map.getIfPresent("k1");
        System.out.println(k1);

        Thread.sleep(5000);

        k1 = map.getIfPresent("k1");
        System.out.println(k1);


    }

}
