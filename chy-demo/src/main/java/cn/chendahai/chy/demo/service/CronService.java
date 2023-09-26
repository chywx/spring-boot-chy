package cn.chendahai.chy.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@EnableScheduling
@Service
@Slf4j
public class CronService {

    public Integer count = 0;

    /**
     * 测试修改系统时间是否会影响定时任务
     */
    @Scheduled(fixedDelay = 5000)
    public void testDelay() {
        log.error("test delay: {} --- {}", new Date(), count++);
    }

}
