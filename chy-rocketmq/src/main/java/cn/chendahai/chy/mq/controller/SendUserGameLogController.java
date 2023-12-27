package cn.chendahai.chy.mq.controller;

import cn.chendahai.chy.mq.config.RocketMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送topic-user-game-log
 */
@RestController
@RequestMapping("/send")
@Slf4j
public class SendUserGameLogController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @RequestMapping("/sendUserGameLog")
    public String sendUserGameLog(@RequestParam(defaultValue = "UserGameLog") String msg, @RequestParam(defaultValue = "1") Integer count) {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendUserGameLog("user game log msg:" + msg);
            log.info("发送的消息：{}", send.toString());
        }
        return "success";
    }


}
