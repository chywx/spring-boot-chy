package cn.chendahai.controller;

import cn.chendahai.queue.RedisDelayQueueEnum;
import cn.chendahai.queue.RedisDelayQueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson/delay")
public class TestDelayQueueController {

    @Autowired
    RedisDelayQueueUtil redisDelayQueueUtil;

    /**
     * 测试结果，是集群模式消费，也没发现消息丢失的情况
     */
    @GetMapping("/sendTimeoutOrder")
    public String sendTimeoutOrder(String orderId) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("orderId", orderId);
        param.put("remark", "订单支付超时，自动取消订单");
        param.put("datetime", "发送时间：" + DateFormat.getDateTimeInstance().format(new Date()));
        // 添加订单支付超时，自动取消订单延迟队列。为了测试效果，延迟10秒钟
        redisDelayQueueUtil.addDelayQueue(param, 10, TimeUnit.SECONDS, RedisDelayQueueEnum.CONSUME_ORDER_PAY_TIMEOUT.getCode());
        return "success";
    }

}
