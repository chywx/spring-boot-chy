package cn.chendahai.chy.mq.controller;

import cn.chendahai.chy.mq.config.RocketMQProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendOrderly")
public class SendOrderlyController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Autowired
    @Qualifier("test1MQProducer")
    private DefaultMQProducer test1MQProducer;


    @RequestMapping("/send")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendOrderly(test1MQProducer, "1>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }


}
