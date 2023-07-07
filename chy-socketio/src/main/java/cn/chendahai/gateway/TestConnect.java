package cn.chendahai.gateway;

import io.socket.client.IO;
import io.socket.client.Socket;

public class TestConnect {

    public static void main(String[] args) {
        //服务端socket.io连接通信地址
        String url = "http://192.16.21.102:2468/demo";
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            options.reconnectionAttempts = 2;
            //失败重连的时间间隔
            options.reconnectionDelay = 1000;
            //连接超时时间(ms)
            options.timeout = 500;
            //userId: 唯一标识 传给服务端存储
            final Socket socket = IO.socket(url + "?userName=dong&appKey=abc-56&roomId=abc-56-1", options);
            socket.on(Socket.EVENT_CONNECT, args1 -> System.out.println(args1));
            socket.on("ewsSocketMsg", objects -> System.out.println("服务端dong:" + objects[0].toString()));
            socket.on("ewsAllClientsMsg", objects -> System.out.println("服务端dong:" + objects[0].toString()));
            socket.connect();

            while (true) {
                Thread.sleep(10000);
                //自定义事件`push_data_event` -> 向服务端发送消息
//                MessageDto messageDto = new MessageDto();
//                messageDto.setSourceUserName("lipenghui");
//                messageDto.setTargetRoom("abc-56-1");
//                messageDto.setMsgType("2");
//                messageDto.setMsgContent("ceshi guangbo");
//                socket.emit("ewsSocketMsg", JSON.toJSONString(messageDto));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
