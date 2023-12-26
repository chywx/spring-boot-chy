package cn.chendahai.queue.handle;


import cn.chendahai.queue.RedisDelayQueueHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ConsumeOrderPayTimeout implements RedisDelayQueueHandle<Map> {


    @Override
    public void execute(Map map) {
        log.info("(收到订单支付超时延迟消息) {}", map);

        //你的业务逻辑代码

    }

}


