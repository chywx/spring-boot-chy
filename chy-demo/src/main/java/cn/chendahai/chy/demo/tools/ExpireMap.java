package cn.chendahai.chy.demo.tools;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

public class ExpireMap {

    public static void main(String[] args) throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .maxSize(100)
                .expiration(1, TimeUnit.SECONDS)
                .expirationPolicy(ExpirationPolicy.ACCESSED)
                .variableExpiration()
                .build();
//        ExpiringMap<String, String> map1 = ExpiringMap.builder().maxSize(1000).build();
        map.put("test", "test123");
        Thread.sleep(500);
        String test = map.get("test");
        Thread.sleep(200);
        Long expiration = map.getExpiration("test");
        Long expectedExpiration = map.getExpectedExpiration("test");
        System.out.println(test);
        System.out.println(expiration);
        System.out.println(expectedExpiration);
    }

}
