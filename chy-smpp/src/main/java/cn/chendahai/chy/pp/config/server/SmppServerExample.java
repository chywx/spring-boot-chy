package cn.chendahai.chy.pp.config.server;

import com.cloudhopper.smpp.SmppServer;
import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.type.SmppChannelException;

public class SmppServerExample {

    public static void main(String[] args) throws Exception {
        SmppServerConfiguration configuration = new SmppServerConfiguration();
        configuration.setPort(18890); // 设置监听端口

        // 创建SMPP服务器实例
        SmppServer smppServer = new DefaultSmppServer(configuration, new MySmppServerSessionHandler());

        try {
            // 启动SMPP服务器
            smppServer.start();
            System.out.println("SMPP服务器已启动，监听端口: " + configuration.getPort());
        } catch (SmppChannelException e) {
            System.err.println("启动SMPP服务器失败: " + e.getMessage());
        }
    }

}
