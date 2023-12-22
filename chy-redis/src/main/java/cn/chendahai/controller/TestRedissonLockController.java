package cn.chendahai.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson")
@Slf4j
public class TestRedissonLockController {

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("/simpleLock1")
    public String simpleLock1(Integer id) {
        log.info(">>>>>> enter simpleLock1");

        RLock lock = redissonClient.getLock("chy:simpleLock1:" + id);

        // 尝试拿锁20s后停止，30s后自动释放
        try {
            boolean b = lock.tryLock(20, 30, TimeUnit.SECONDS);
            if (!b) {
                log.info("get lock failed:" + Thread.currentThread().getName());
                return "failed";
            }
            log.info("get lock success:" + Thread.currentThread().getName());
            return "success";

        } catch (InterruptedException e) {
            log.error("simpleLock1 error", e);
            return "error";
        } finally {
            lock.unlock();
        }

    }

    @RequestMapping("/simpleLock2")
    public String simpleLock2(Integer id) {
        log.info(">>>>>> enter simpleLock2");

        RLock lock = redissonClient.getLock("chy:simpleLock2:" + id);
        // 不设置leaseTime，或者设置为-1  开启看门狗机制
        try {
            boolean b = lock.tryLock(10, TimeUnit.SECONDS);
            if (!b) {
                log.info("get lock failed:" + Thread.currentThread().getName());
                return "failed";
            }
            log.info("get lock success:" + Thread.currentThread().getName());
            return "success";

        } catch (InterruptedException e) {
            log.error("simpleLock1 error", e);
            return "error";
        } finally {
            lock.unlock();
        }

    }

}
