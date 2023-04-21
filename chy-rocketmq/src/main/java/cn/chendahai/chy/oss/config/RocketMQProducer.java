package cn.chendahai.chy.oss.config;

import cn.chendahai.chy.oss.enums.MQGroup;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configuration
public class RocketMQProducer {

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;
    /**
     * 消息发送超时时间，默认3秒
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducer.class);

    @Bean
    public DefaultMQProducer test1MQProducer() {
        return getDefaultMQProducer(MQGroup.DEMO1);
    }

    @Bean
    public DefaultMQProducer test2MQProducer() {
        return getDefaultMQProducer(MQGroup.DEMO2);
    }

    @Bean
    public DefaultMQProducer test3MQProducer() {
        return getDefaultMQProducer(MQGroup.DEMO3);
    }

    private DefaultMQProducer getDefaultMQProducer(MQGroup group) {
        DefaultMQProducer producer = new DefaultMQProducer(group.getProducerName());
        producer.setNamesrvAddr(namesrvAddr);// 地址
//        producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
        producer.setMaxMessageSize(this.maxMessageSize);// 消息大小
        producer.setSendMsgTimeout(this.sendMsgTimeout);// 超时时间
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);// 重置次数
        try {
            producer.start();
            logger.info("producer is start ! addr:{}, groupName:{}", this.namesrvAddr, group.getProducerName());
            return producer;
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SendResult send(DefaultMQProducer producer, String msg) {
        try {
            String producerGroup = producer.getProducerGroup();
            MQGroup mqGroup = MQGroup.getByProducerName(producerGroup);

            Message message = new Message();
            message.setTopic(mqGroup.getTopic());
            message.setBody(msg.getBytes());

            return producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public SendResult sendOrderly(DefaultMQProducer producer, String msg) {
        try {
            String producerGroup = producer.getProducerGroup();
            MQGroup mqGroup = MQGroup.getByProducerName(producerGroup);

            Message message = new Message();
            message.setTopic(mqGroup.getTopic());
            message.setBody(msg.getBytes());

            return producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % list.size();
                    return list.get(index);
                }
            }, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public SendResult sendDelay(DefaultMQProducer producer, String msg, Integer delayTimeLevel) {
        try {
            String producerGroup = producer.getProducerGroup();
            MQGroup mqGroup = MQGroup.getByProducerName(producerGroup);

            Message message = new Message();
            message.setTopic(mqGroup.getTopic());
            message.setBody(msg.getBytes());

            message.setDelayTimeLevel(delayTimeLevel);

            return producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
