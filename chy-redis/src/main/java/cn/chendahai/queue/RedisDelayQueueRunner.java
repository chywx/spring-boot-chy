package cn.chendahai.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 系统启动时加载消费队列
 */
@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) {

        RedisDelayQueueEnum[] queueEnums = RedisDelayQueueEnum.values();
        //每个消费者启动一个固定线程，以防止某个消费者在没有消息消费时调用getBlockingDeque（阻塞双端队列）一直被阻塞进而导致其他的消费者有消息消费也被阻塞住.
        for (RedisDelayQueueEnum queueEnum : queueEnums) {
            new Thread(() -> {
                while (true) {
                    try {
                        Object value = redisDelayQueueUtil.getDelayQueue(queueEnum.getCode());
                        if (value != null) {
                            RedisDelayQueueHandle redisDelayQueueHandle = applicationContext.getBean(queueEnum.getBeanId(), RedisDelayQueueHandle.class);
                            redisDelayQueueHandle.execute(value);
                        }
                        System.out.println();

                    } catch (Exception e) {
                        log.error("(Redis延迟队列异常中断) {}", e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        log.info("(Redis延迟队列启动成功)");
    }
}
