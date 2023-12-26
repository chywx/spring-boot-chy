package cn.chendahai.queue;

/**
 * 延迟队列执行器  消费类需要实现此接口
 */
public interface RedisDelayQueueHandle<T> {

    /**
     * 执行方法
     *
     * @param t 执行类
     */
    void execute(T t);
}