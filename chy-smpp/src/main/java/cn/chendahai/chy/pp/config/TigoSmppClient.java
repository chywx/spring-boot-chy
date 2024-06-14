package cn.chendahai.chy.pp.config;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TigoSmppClient {

    public static void main(String[] args) throws Exception {
        start(args);
    }

    public static void start(String[] args) throws Exception {

        for (String arg : args) {
            System.out.println("arg\t" + arg);
        }

        //        List<String> list = Arrays.asList("+255781364276",
//                "+255692724798", "+255757396600", "255615100579", "0652924502");
//        List<String> list = Arrays.asList("+255658123798","255712786100");

        SmppSessionConfiguration config = new SmppSessionConfiguration();
        config.setHost("41.222.182.51");
        config.setPort(10501);
        config.setSystemId("******");
        config.setPassword("B@h&2St8");
        config.setSystemType("TR");

        DefaultSmppClient client = new DefaultSmppClient();
        log.info("client {}", client);

        SmppSession session = client.bind(config, new ClientSmppSessionHandler());
        log.info("session {}", session);

        List<String> list = Arrays.asList(args);
        List<Integer> tonList = Arrays.asList(0x00, 0x01, 0x02);
//        List<Integer> tonList = Arrays.asList(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06);
//        List<Integer> tonList = Arrays.asList(00000000, 00000001, 00000010, 00000011, 00000100, 00000101, 00000110);
        List<Integer> npiList = Arrays.asList(0x00, 0x01);
//        List<Integer> npiList = Arrays.asList(0x00, 0x01, 0x03, 0x04, 0x06, 0x08, 0x09, 0x10);
//        List<Integer> npiList = Arrays.asList(00000000, 00000001, 00000011, 00000100, 00000110, 00001000, 00001001, 00001010);
        log.info("list {}", list);

        Map<String, String> map = new HashMap<>();

        for (String phone : list) {

            for (Integer tonSource : tonList) {
                for (Integer npiSource : npiList) {
//                    for (Integer tonDest : tonList) {
//                        for (Integer npiDest : npiList) {


                    SubmitSm sm = new SubmitSm();

                    sm.setServiceType(null);
//                    sm.setServiceType("SMS");

//                    sm.setSourceAddress(new Address(Byte.parseByte(tonSource.toString()), Byte.parseByte(npiSource.toString()), "BAHATISHA"));
//                    sm.setDestAddress(new Address(Byte.parseByte(tonSource.toString()), Byte.parseByte(npiSource.toString()), phone));
//                    sm.setSourceAddress(new Address(Byte.parseByte(tonSource.toString()), Byte.parseByte(npiSource.toString()), "BAHATISHA"));
//                    sm.setDestAddress(new Address(Byte.parseByte(tonSource.toString()), Byte.parseByte(npiSource.toString()), phone));
                    sm.setSourceAddress(new Address((byte) (int) tonSource, (byte) (int) npiSource, "BAHATISHA"));
                    sm.setDestAddress(new Address((byte) (int) tonSource, (byte) (int) npiSource, phone));
                    sm.setShortMessage(new String("Hello, World!" + LocalDateTime.now()).getBytes());


                    try {
                        SubmitSmResp submitSmResp = session.submit(sm, 10000);
                        log.info(">>>>>>result {} {}", phone, submitSmResp);

//                        map.put(tonSource + ":" + npiSource + ":" + tonDest + ":" + npiDest, submitSmResp.getResultMessage());
                        map.put(tonSource + ":" + npiSource, submitSmResp.getResultMessage());
                        Set<String> collect = map.values().stream().collect(Collectors.toSet());
                        System.out.println("collect>>>:" + collect);

                        System.out.println("result message: " + submitSmResp.getResultMessage());
                        System.out.println("messageId: " + submitSmResp.getMessageId());
                        System.out.println("commandId: " + submitSmResp.getCommandId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//            System.out.println(submitSmResp.getCommandId());
//            System.out.println(submitSmResp.getName());
//            System.out.println(submitSmResp.getReferenceObject());


//            Class<SubmitSmResp> responseClass = sm.getResponseClass();
//            SubmitSmResp response = responseClass.newInstance();
//            System.out.println("response.getMessageId()" + response.getMessageId());

                }
//            }
//        }
            }


        }

        System.out.

                println("map>>>:" + map);


// 关闭会话
        if (session.

                isBound()) {
            session.

                    unbind(5000);
        }
    }

    public static class ClientSmppSessionHandler extends DefaultSmppSessionHandler {

        public ClientSmppSessionHandler() {
            super(log);
        }

        @Override
        public void firePduRequestExpired(PduRequest pduRequest) {
            log.warn("PDU request expired: {}", pduRequest);
        }

        @Override
        public PduResponse firePduRequestReceived(PduRequest pduRequest) {
            PduResponse response = pduRequest.createResponse();

            // do any logic here

            return response;
        }

    }

}

class OctetString {

    private byte[] bytes;

    public OctetString(byte[] byteArray) {
        this.bytes = byteArray;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}