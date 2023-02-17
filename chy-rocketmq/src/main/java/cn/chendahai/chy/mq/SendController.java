package cn.chendahai.chy.mq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述
 *
 * @author chy
 * @date 2019/9/20 0020
 */
@RestController
@RequestMapping("/msg")
public class SendController {

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    public static void main(String[] args) {

        System.out.println(TimeUnit.HOURS.toSeconds((24)));

        Boolean bool = null;
        if (bool) {
            System.out.println(123);
        } else {
            System.out.println(333);
        }

    }

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
