package cn.chendahai.chy.demo.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    /**
     * 异步任务线程池配置
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 设置线程初始化数量
        executor.setMaxPoolSize(100); // 设置线程池最大数量
        executor.setQueueCapacity(100); // 设置等待队列的大小
        executor.setThreadNamePrefix("chy-"); // 设置线程名称前缀
        executor.initialize(); //如果不初始化，导致找到不到执行器
        return executor;
    }
    /**
     * 异步任务异常处理
     * 如果需要自定义对异步任务的异常进行处理，则自定义异常处理（实现AsyncUncaughtExceptionHandler接口），并在这个方法返回该对象
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}