package cn.chendahai.chy.service;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketService {
    @Bean
    public SocketIOServer socketIOServer() {
        System.out.println("init socket server");


        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
//        configuration.setHostname("127.0.0.1");
        configuration.setHostname("192.16.21.102");
        configuration.setPort(2468);
        configuration.setContext("/xiaofen");

        // 开启socket端口复用
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        configuration.setSocketConfig(socketConfig);

        // 设置工作线程数
        configuration.setWorkerThreads(100);
        // 设置允许客户端访问
        configuration.setAllowCustomRequests(true);
        //协议升级超时时间(毫秒)，默认10秒，HTTP握手升级为ws协议超时时间
        configuration.setUpgradeTimeout(10000);
        //Ping消息超时时间(毫秒)，默认60秒，这个时间间隔内没有接收到心跳消息就会发送超时事件
        configuration.setPingTimeout(600000);
        //Ping消息间隔(毫秒)，默认25秒。客户端向服务器发送一条心跳消息间隔
        configuration.setPingInterval(25000);
        //设置最大每帧处理数据的长度，防止他人利用大数据来攻击服务器       config.setMaxFramePayloadLength(maxFramePayloadLength);
        configuration.setTransports(Transport.POLLING, Transport.WEBSOCKET);
//        configuration.setOrigin("*");

        return new SocketIOServer(configuration);

    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }


}
