package cn.chendahai.chy.test.config;


import cn.chendahai.chy.test.enums.MQGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RocketMQConsumer {
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Bean
    public DefaultMQPushConsumer demo1Consumer() {
        return getDefaultMQPushConsumer(MQGroup.DEMO1);
    }

    @Bean
    public DefaultMQPushConsumer demo2Consumer() {
        return getDefaultMQPushConsumer(MQGroup.DEMO2);
    }

    @Bean
    public DefaultMQPushConsumer demo3Consumer() {
        return getDefaultMQPushConsumer(MQGroup.DEMO3);
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer(MQGroup group) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group.getConsumerName());
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);

        MessageListener listener = group.getListener();
        if (listener instanceof MessageListenerConcurrently) {
            consumer.registerMessageListener((MessageListenerConcurrently) listener);
        }
        if (listener instanceof MessageListenerOrderly) {
            consumer.registerMessageListener((MessageListenerOrderly) listener);
        }

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        try {
            consumer.subscribe(group.getTopic(), "*");
            consumer.start();
            log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", group.getConsumerName(), group.getTopic(), namesrvAddr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return consumer;
    }


}
