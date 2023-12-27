package cn.chendahai.chy.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UserGameLog1Listener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.error("UserGameLog【1】Listener", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        if (CollectionUtils.isEmpty(list)) {
            log.error("消息为空，不做处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        log.info("UserGameLog【1】Listener接收到的消息：{}", msg);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


}
