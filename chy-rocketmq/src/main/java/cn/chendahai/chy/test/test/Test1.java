package cn.chendahai.chy.test.test;

import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageId;

import java.net.UnknownHostException;

public class Test1 {

    public static void main(String[] args) throws UnknownHostException {
        MessageId messageId = MessageDecoder.decodeMessageId("C0A884C900002A9F00000000001D6CDA");
//        MessageId messageId = MessageDecoder.decodeMessageId("7F0000014E7818B4AAC25662C53A001A");
        System.out.println(messageId);
    }
}
