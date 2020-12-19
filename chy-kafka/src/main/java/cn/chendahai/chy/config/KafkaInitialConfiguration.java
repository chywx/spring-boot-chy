package cn.chendahai.chy.config;/**
 * @author chy
 * @date 2020/12/2 0002 下午 16:00
 * Description：
 */

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/2 0002
 */

@Configuration
public class KafkaInitialConfiguration {

    // 创建一个名为testtopic的Topic并设置分区数为8，分区副本数为2
    @Bean
    public NewTopic initialTopic() {
        System.out.println("initialTopic");
        return new NewTopic("test-topic", 8, (short) 1);
    }

}
