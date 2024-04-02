package cn.chendahai.chy.pp.config.client;

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

@Slf4j
public class SmppClient {
    public static void main(String[] args) throws Exception {
        SmppSessionConfiguration config = new SmppSessionConfiguration();
        config.setHost("localhost");
        config.setPort(18890);
        config.setSystemId("test01");
        config.setPassword("1qaz2wsx");

        DefaultSmppClient client = new DefaultSmppClient();

        SmppSession session = client.bind(config, new ClientSmppSessionHandler());

        // Prepare and send the "Hello, World!" message
        for (int i = 0; i < 2; i++) {
            SubmitSm sm = new SubmitSm();
            sm.setSourceAddress(new Address((byte) 0x03, (byte) 0x00, "SENDER"));
            sm.setDestAddress(new Address((byte) 0x01, (byte) 0x01, "RECIPIENT"));
            sm.setShortMessage(new String("Hello, World!" + i).getBytes());
            SubmitSmResp submitSmResp = session.submit(sm, 10000);
            log.info("result {} {}", i, submitSmResp);
        }

        // 关闭会话
        if (session.isBound()) {
            session.unbind(5000);
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
