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


    @RequestMapping("/send")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer delayTimeLevel) {

        SendResult send = rocketMQProducer.sendDelayDemo1("topic1>>>发送delay消息，发送时间为：" + LocalDateTime.now() + "延迟级别：" + delayTimeLevel + "消息为：" + msg, delayTimeLevel);
        System.out.println("发送的延迟消息级别：" + delayTimeLevel);
        System.out.println("发送的延迟消息消息：" + send.toString());
        return "success";
    }


}
