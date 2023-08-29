package cn.chendahai.chy.mq.enums;

import cn.chendahai.chy.mq.listener.*;
import org.apache.rocketmq.client.consumer.listener.MessageListener;

/**
 * @date 2020/08/27
 * @time 10:30
 */
public enum MQGroup {
    DEMO1("demo1", "topic-demo1", "producer-demo1", "consumer-demo1", new Demo1Listener()),

    DEMO2("demo2", "topic-demo2", "producer-demo2", "consumer-demo2", new Demo2Listener()),

    DEMO3("demo3", "topic-demo3", "producer-demo3", "consumer-demo3", new Demo3Listener()),

    POKER1("poker1", "topic-poker1", "producer-poker1", "consumer-poker1", new Poker1Listener()),

    ALIKE1("alike1", "topic-alike1", "producer-alike", "consumer-alike", new Alike1Listener()),
    ALIKE2("alike2", "topic-alike2", "producer-alike", "consumer-alike", new Alike2Listener()),

    ALIKE3("alike3", "topic-alike3", "producer-alike", "consumer-alike", new Alike3Listener()),
    ;

    public static MQGroup getByProducerName(String producerName) {
        for (MQGroup value : values()) {
            if (value.getProducerName().equals(producerName)) {
                return value;
            }
        }
        return null;
    }

    MQGroup(String desc, String topic, String producerName, String consumerName, MessageListener listener) {
        this.desc = desc;
        this.topic = topic;
        this.producerName = producerName;
        this.consumerName = consumerName;
        this.listener = listener;
    }

    private String desc;
    private String topic;
    private String producerName;
    private String consumerName;

    private MessageListener listener;

    public String getDesc() {
        return desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getProducerName() {
        return producerName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public MessageListener getListener() {
        return listener;
    }
}
