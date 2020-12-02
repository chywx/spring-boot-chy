package cn.chendahai.chy.controller;/**
 * @author lql
 * @date 2020/12/2 0002 下午 16:10
 * Description：
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/12/2 0002
 */
@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // 发送消息
    @GetMapping("/send/simple")
    public void sendMessage1(String message) {
        kafkaTemplate.send("topic1", message);
    }

}
