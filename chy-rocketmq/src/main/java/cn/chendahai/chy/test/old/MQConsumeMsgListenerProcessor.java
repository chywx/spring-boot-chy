package cn.chendahai.chy.test.old;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
//public class MQConsumeMsgListenerProcessor implements MessageListenerOrderly {

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            TimeUnit.SECONDS.sleep(1);

            MessageExt messageExt = list.get(0);
            System.out.println("chy>>>>" + new String(messageExt.getBody()));
//            int i = 1 / 0;

        } catch (Exception e) {
            log.error("Concurrently", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("消息为空，不做处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        log.info("接收到的消息：{}", msg);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    //    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        try {
            TimeUnit.SECONDS.sleep(1);

//            int i = 1 / 0;

        } catch (Exception e) {
            log.error("Orderly", e);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("consumeMessage 消息为空，不做处理");
            return ConsumeOrderlyStatus.SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        log.info("consumeMessage 接收到的消息：{}", msg);
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
