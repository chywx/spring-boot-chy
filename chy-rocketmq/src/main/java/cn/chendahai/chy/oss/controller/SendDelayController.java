package cn.chendahai.chy.oss.controller;

import cn.chendahai.chy.oss.config.RocketMQProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sendDelay")
public class SendDelayController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Autowired
    @Qualifier("test1MQProducer")
    private DefaultMQProducer test1MQProducer;


    @RequestMapping("/send")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer delayTimeLevel) {

        SendResult send = rocketMQProducer.sendDelay(test1MQProducer, "topic1>>>发送delay消息，发送时间为：" + LocalDateTime.now() + "延迟级别：" + delayTimeLevel + "消息为：" + msg, delayTimeLevel);
        System.out.println("发送的消息：" + send.toString());
        return "success";
    }


}
