package cn.chendahai.chy.oss.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Poker1Listener implements MessageListenerOrderly {

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {

        if (consume(list)) {
            return ConsumeOrderlyStatus.SUCCESS;
        } else {
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
    }

    public boolean consume(List<MessageExt> list) {
        try {
            TimeUnit.SECONDS.sleep(100);

//            MessageExt messageExt = list.get(0);
//            System.out.println("Demo1Listener>>>>" + new String(messageExt.getBody()));
//            int i = 1 / 0;

        } catch (Exception e) {
            log.error("Demo1Listener error", e);
            return false;
        }
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("消息为空，不做处理");
            return true;
        }
        MessageExt messageExt = list.get(0);
        String msg = new String(messageExt.getBody());
        log.info("OrderlyDemo1Listener接收到的消息：{}", msg);
        return true;
    }


}
