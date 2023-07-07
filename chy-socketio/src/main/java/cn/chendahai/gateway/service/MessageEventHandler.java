package cn.chendahai.gateway.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MessageEventHandler {

    @Autowired
    private SocketIOServer socketIoServer;

    @PostConstruct
    private void start(){
        try {
            socketIoServer.start();
            log.info("socketServer is start");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnConnect
    private void onConnect(SocketIOClient client) {
        log.error("OnConnect >>> {}", client.getSessionId());
    }

    @OnEvent(value = "on_login")
    private void userLogin(SocketIOClient client, String token) {
        log.error("user login {} {}", client.getSessionId(), token);
    }

    @OnDisconnect
    private void onDisconnect(SocketIOClient client) {
        log.error("onDisconnect >>> {}", client.getSessionId());
    }

}
