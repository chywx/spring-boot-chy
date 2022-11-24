package cn.chendahai.chy.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Dylan
 * @date: 2022/10/28 18:29
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/baobao")    // 指定websocket 连接的url
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        log.info("客户端：{}连接成功", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("客户端：{}连接断开", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    @OnMessage
    public String onMsg(String message, Session session) {
        log.info("从客户端：{} 收到<--:{}", session.getId(), message);

        if ("hello".equals(message)) {
            return "world";
        }
        if ("chy".equals(message)) {
            return "大海";
        }
        if ("lhg".equals(message)) {
            return "小歌";
        }

        String send = message.toUpperCase();
        String result = "客户：%s您好，来自server 的消息:%s";
        result = String.format(result, session.getId(), send);
        return "来自server 的消息：" + result;
    }

    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.error("sendInfo", e);
            }
        }
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}