package cn.chendahai.chy.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/sendTest")
    public String sendTest() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String msg = "haha";
        for (int i = 0; i < 50; i++) {
            Message message = new Message("DemoTopic-chy", "*", (msg + i).getBytes());
            SendResult send = defaultMQProducer.send(message);
            System.out.println("发送的消息：" + send.toString());
        }
        return "success";
    }
}
