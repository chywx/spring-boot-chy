package cn.chendahai.chy.pp.config.client;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;

public class SmppClient {
    public static void main(String[] args) throws Exception {
        SmppSessionConfiguration config = new SmppSessionConfiguration();
        config.setHost("localhost");
        config.setPort(18890);
        config.setSystemId("test01");
        config.setPassword("1qaz2wsx");

        DefaultSmppClient client = new DefaultSmppClient();
        SmppSession session = client.bind(config, new MySmppSessionHandler());

        session = client.bind(config, new MySmppSessionHandler());

        // Prepare and send the "Hello, World!" message
        for (int i = 0; i < 120; i++) {
            SubmitSm sm = new SubmitSm();
            sm.setSourceAddress(new Address((byte) 0x03, (byte) 0x00, "SENDER"));
            sm.setDestAddress(new Address((byte) 0x01, (byte) 0x01, "RECIPIENT"));
            sm.setShortMessage("Hello, World!".getBytes());
            session.submit(sm, 10000);
        }

        // 关闭会话
        if (session != null && session.isBound()) {
            session.unbind(5000);
        }
    }
}
