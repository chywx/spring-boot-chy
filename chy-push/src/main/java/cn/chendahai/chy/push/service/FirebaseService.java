package cn.chendahai.chy.push.service;

import com.alibaba.fastjson.JSON;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Penggq
 * @since 2020/1/7 16:43
 */
@Service
public class FirebaseService {

    private static Logger logger = LoggerFactory.getLogger(FirebaseService.class);

    @Value("${firebase.privateKeyFile}")
    private String privateKeyFile;

    @Value("${firebase.databaseUrl}")
    private String databaseUrl;

    private static FirebaseService firebaseService;


    @PostConstruct
    public void init() {

        FirebaseApp firebaseApp = null;
        try {
            FileInputStream serviceAccount =
                    new FileInputStream(privateKeyFile);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl(databaseUrl)
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
            // "[DEFAULT]"
            logger.info("Firebase Init, {}", firebaseApp.getName());
            firebaseService = this;
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> tokenList = new ArrayList<>();
        // 7
        String token7 = "fOMI9fiEQ1ODd7_Te66teu:APA91bGK3RWVxoPBjIQujnu90qUUHwNq0xgl4y9PdK4YA4I9aeTeizuHXOlYpaY_J2cIv1QCz2Uhdc4x3KqedIyynFEh2dM5uRD8khtxmUsWNin_KrAnQ43x4S4221go-lNqOLsdpXt3";
        // chy
        String tokenchy = "coY4PowNQ4yNC2QcDLNs7L:APA91bE4pdDVzkDdBduY-cyGuDwIPRNQM307EXIFXl0oTn6BiJQXWjSue3HYikK__2QjwKisqGpR7IyIS2FZ4kunU7hSkvqiJIdbVBExvtvz0K58i0KBgFfWg9pSHVBD9k1O9d0Vtyuf";
        tokenList.add(token7);
        tokenList.add(tokenchy);
        tokenList.add(tokenchy.replace("c", "a"));


//                Message message = Message.builder().putData("target", msg.getTarget()).putData("param", msg.getParam()).putData("title", msg.getTitle()).putData("content", msg.getContent())
//        Message message = Message.builder().putData("target","bangbet://deposit").putData("param","").putData("title","hello world").putData("content","hello, welcome to bangbet")
//        Message message = Message.builder().putData("target", "bangbet://live?sportId=").putData("param", "sr:sport:12").putData("title", "hello world").putData("content", "hi,welcome to bangbet")
//                .setAndroidConfig(AndroidConfig.builder()
//                        .setTtl(3600 * 1000)
//                        .build())
//                .setToken(token)
//                .build();
//        Message message = Message.builder()
//                .putData("score", "850")
//                .putData("time", "2:45")
//                .setToken(token)
//                .build();

        System.out.println(LocalDateTime.now());

        for (String token : tokenList) {
            Message message = Message.builder()
                    .setToken(token)
//                    .putData("score", "850")
//                    .putData("time", "2:45")
                    .setNotification(Notification.builder()
                            .setTitle("title")
                            .setBody("body body")
                            .build())
                    .build();

            try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            System.out.println(response);
                BatchResponse batchResponse = FirebaseMessaging.getInstance().sendAll(Collections.singletonList(message));
                for (SendResponse respons : batchResponse.getResponses()) {
                    System.out.println(respons);
                    System.out.println(respons.getMessageId());
                    System.out.println(respons.isSuccessful());
                    System.out.println(JSON.toJSONString(respons));
                }
            } catch (FirebaseMessagingException e) {
                logger.error("sendAll error", e);
            }

            try {
                System.out.println(">>>send start");
                String send = FirebaseMessaging.getInstance().send(message);
                System.out.println(">>>" + send);
                System.out.println(">>>send end");

            } catch (FirebaseMessagingException e) {
                System.out.println(e.getMessage());
                logger.error("send error", e);
            }


        }
        System.out.println(">>>end");
    }

}
