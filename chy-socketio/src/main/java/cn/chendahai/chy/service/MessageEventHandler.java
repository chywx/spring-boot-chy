package cn.chendahai.chy.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnMessage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MessageEventHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    // 用来存已连接的客户端
    private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void start() {
        try {
            socketIOServer.start();
            log.info("socketServer is start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnConnect
    private void onConnect(SocketIOClient client) {
        log.error("OnConnect >>> {}", client.getSessionId());
        log.error("params: {}", client.getHandshakeData().getUrlParams());
    }

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        log.error("onDisconnect >>> {}", client.getSessionId());
    }

    @OnEvent(value = "test_ping")
    public void testPing(SocketIOClient client, String info) {
        System.out.println("test_ping事件收到消息：" + info);
        client.sendEvent("test_pong", "服务端回复：" + info);
    }


    /**
     * 此方法为获取client连接中的参数，可根据需求更改
     *
     * @param client
     * @return
     */
//    private String getParamsByClient(SocketIOClient client) {
//        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
//        System.out.println(client.getHandshakeData().getUrlParams());
//        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
//        List<String> list = params.get("userId");
//        if (list != null && list.size() > 0) {
//            return list.get(0);
//        }
//        return null;
//    }

}
