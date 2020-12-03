package cn.chendahai.chy.mq;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("list:" + list.size());
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("消息为空，不做处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        System.out.println("接收到的消息：" + msg);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
