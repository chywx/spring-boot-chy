package cn.chendahai.chy;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * 功能描述
 *
 * @author chy
 * @date 2019/7/22 0022
 */
public class DemoSocketClient {
    public static void main(String[] args) {
        try {
            IO.Options options = new IO.Options();
            options.path = "/xiaofen";
            options.query = "userId=1234";
            final Socket socket = IO.socket("http://127.0.0.1:2468", options);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connect success");
                    socket.emit("test_ping", "hahaha");
//                    socket.close();
                }
            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connect timeout");
                }
            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connect error");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("disconnect");
                }
            }).on("notice_info", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String data = (String) args[0];
                }
            });
            socket.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
