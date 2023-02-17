package cn.chendahai.chy.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQProducerConfiguration {

    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize ;
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

    @Bean
    public DefaultMQProducer getDefaultMQProducer(){
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(namesrvAddr);// 地址
//        producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");
        producer.setMaxMessageSize(this.maxMessageSize);// 消息大小
        producer.setSendMsgTimeout(this.sendMsgTimeout);// 超时时间
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);// 重置次数
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                , this.groupName, this.namesrvAddr));
        return producer;
    }

}
