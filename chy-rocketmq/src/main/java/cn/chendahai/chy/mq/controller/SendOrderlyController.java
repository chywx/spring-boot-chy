package cn.chendahai.chy.mq.controller;

import cn.chendahai.chy.mq.config.RocketMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendOrderly")
public class SendOrderlyController {

    @Autowired
    private RocketMQProducer rocketMQProducer;


    @RequestMapping("/sendOrderlyDemo1")
    public String sendOrderlyDemo1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count, @RequestParam(defaultValue = "0") Integer arg) {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendOrderlyDemo1("topic1>>>" + msg, arg);
            System.out.println("发送的顺序消息arg：" + arg);
            System.out.println("发送的顺序消息消息：" + send.toString());
        }
        return "success";
    }

    @RequestMapping("/sendOrderlyPoker1")
    public String sendOrderlyPoker1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count, @RequestParam(defaultValue = "0") Integer arg) {
        for (int i = 1; i <= count; i++) {
            msg = "poker1>>>" + msg + "\t>>>第" + i + "条";
            SendResult send = rocketMQProducer.sendOrderlyPoker1(msg, arg);
            System.out.println("发送的顺序消息arg：" + arg);
            System.out.println("发送的顺序消息消息：" + send.toString());
        }
        return "success";
    }


}
