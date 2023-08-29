package cn.chendahai.chy.mq.controller;

import cn.chendahai.chy.mq.config.RocketMQProducer;
import cn.chendahai.chy.mq.enums.MQGroup;
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
    DefaultMQProducer alike1MQProducer;

    @Autowired
    DefaultMQProducer alike2MQProducer;

    @Autowired
    DefaultMQProducer alike3MQProducer;

    @RequestMapping("/sendByTopic1")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendDemo1("topic1>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopic2")
    public String sendByTopic2(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendDemo2("topic2>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopic3")
    public String sendByTopic3(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendDemo3("topic3>>>" + msg);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopicAlike1")
    public String sendByTopicAlike1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            Message message = new Message();
            message.setTopic(MQGroup.ALIKE1.getTopic());
            message.setBody(msg.getBytes());
            SendResult send = alike1MQProducer.send(message);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopicAlike2")
    public String sendByTopicAlike2(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            Message message = new Message();
            message.setTopic(MQGroup.ALIKE2.getTopic());
            message.setBody(msg.getBytes());
            SendResult send = alike2MQProducer.send(message);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendByTopicAlike3")
    public String sendByTopicAlike3(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 1; i <= count; i++) {
            Message message = new Message();
            message.setTopic(MQGroup.ALIKE3.getTopic());
            message.setBody(msg.getBytes());
            SendResult send = alike3MQProducer.send(message);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }


    @Autowired
    @Qualifier("defaultMQProducer")
    private DefaultMQProducer defaultMQProducer;


    /**
     * 使用默认的生产者进行消息发送
     */
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
