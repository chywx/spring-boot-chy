package cn.chendahai.chy.controller;/**
 * @author lql
 * @date 2020/12/2 0002 下午 16:10
 * Description：
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
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

    @GetMapping("/send/callbackOne")
    public void sendMessage2(String message) {
        kafkaTemplate.send("topic1", message).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
    }

    @GetMapping("/send/callbackTwo")
    public void sendMessage3(String message) {
        kafkaTemplate.send("topic1", message).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败：" + ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                    + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });
    }

}
