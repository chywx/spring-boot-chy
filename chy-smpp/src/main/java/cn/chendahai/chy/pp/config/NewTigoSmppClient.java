package cn.chendahai.chy.pp.config;

import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.*;
import com.cloudhopper.smpp.type.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class NewTigoSmppClient {


    @RequestMapping("/tigo/send")
    public String send(String phone, String sid) throws Exception {
        log.info("phone:{}", phone);

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

        SubmitSm sm = new SubmitSm();
        sm.setServiceType("CMT");
        sm.setSourceAddress(new Address((byte) 0x01, (byte) 0x01, sid));
        sm.setDestAddress(new Address((byte) 0x01, (byte) 0x01, phone));
        sm.setShortMessage(new String("Hello, World!" + LocalDateTime.now()).getBytes());

        try {
            SubmitSmResp submitSmResp = session.submit(sm, 10000);
            log.info(">>>>>>result {} {}", phone, submitSmResp);
            System.out.println("result message: " + submitSmResp.getResultMessage());
            System.out.println("messageId: " + submitSmResp.getMessageId());
            System.out.println("commandId: " + submitSmResp.getCommandId());
//            EnquireLink enquireLink = new EnquireLink();
//            EnquireLinkResp enquireLinkResp = session.enquireLink(enquireLink, 10000);
//            log.info("enquireLinkResp {}", enquireLinkResp);
            QuerySm querySm = new QuerySm();
            querySm.setMessageId(submitSmResp.getMessageId());

            WindowFuture<Integer, PduRequest, PduResponse> future = session.sendRequestPdu(querySm, 10000, true);

//            future.complete(new QuerySmResp());
            future.await(8000);

            log.info(">>>>>>future {}", future);
            PduResponse response = future.getResponse();
            log.info(">>>>>>response {}", response);


        } catch (Exception e) {
            e.printStackTrace();
        }
//        }

        // 关闭会话
        if (session.isBound()) {
            session.unbind(5000);
        }
        return "success:" + LocalDateTime.now();
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
            return response;
        }

    }

}