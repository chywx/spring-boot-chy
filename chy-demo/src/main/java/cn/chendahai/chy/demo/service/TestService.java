package cn.chendahai.chy.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@Slf4j
public class TestService {


    /**
     * 异步生效
     *
     * @param msg
     */
    @Async
    public void print(String msg) {
        log.info("msg:{}", msg);
    }

    /**
     * 异步生效，获取不到返回值
     *
     * @param msg
     * @return
     */
    @Async
    public String printReturnString(String msg) {
        log.info("msg:{}", msg);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return "print return " + msg;
    }

    /**
     * 异步生效，通过future来获取返回值
     */
    @Async
    public Future<String> printReturnFuture(String msg) {
        log.info("msg:{}", msg);
        String string = "msg-" + msg;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        AsyncResult<String> result = new AsyncResult<>(string);
        return result;
    }

    /**
     * 异步生效，通过    public Future<String> printReturnCompletableFuture(String msg){来获取返回值
     */
    @Async("asyncExecutor")
    public CompletableFuture<String> printReturnCompletableFuture(String msg) {
        log.info("msg:{}", msg);
        String string = "msg-" + msg;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(string);
    }


}
