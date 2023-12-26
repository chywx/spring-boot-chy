package cn.chendahai.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RedisDelayQueueEnum {
    CONSUME_ORDER_PAY_TIMEOUT("CONSUME_ORDER_PAY_TIMEOUT","订单支付超时，自动取消订单", "consumeOrderPayTimeout"),
    CONSUME_JOINT_TIMOUT("CONSUME_JOINT_TIMOUT", "拼团超时，取消拼团", "consumeJointTimout");

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;
}
