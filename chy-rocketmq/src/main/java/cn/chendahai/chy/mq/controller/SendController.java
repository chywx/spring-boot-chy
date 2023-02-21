package cn.chendahai.chy.mq.controller;

import cn.chendahai.chy.mq.config.RocketMQProducer;
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

@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @Autowired
    @Qualifier("test1MQProducer")
    private DefaultMQProducer test1MQProducer;

    @Autowired
    @Qualifier("test2MQProducer")
    private DefaultMQProducer test2MQProducer;

    @Autowired
    @Qualifier("test3MQProducer")
    private DefaultMQProducer test3MQProducer;

    @RequestMapping("/sendByTopic1")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.send(test1MQProducer, "1>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopic2")
    public String sendByTopic2(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.send(test2MQProducer, "2>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopic3")
    public String sendByTopic3(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.send(test3MQProducer, "3>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }


    @Autowired
    @Qualifier("defaultMQProducer")
    private DefaultMQProducer defaultMQProducer;

    @RequestMapping("/sendTest")
    public String sendTest(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            Message message = new Message("test-a", "*", (msg + i).getBytes());
            SendResult send = defaultMQProducer.send(message);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }


}
