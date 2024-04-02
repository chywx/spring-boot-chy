package cn.chendahai.chy.pp.config.server;

import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.SmppProcessingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySmppServerSessionHandler implements SmppServerHandler {
    @Override
    public void sessionBindRequested(Long sessionId, SmppSessionConfiguration smppSessionConfiguration, BaseBind baseBind) throws SmppProcessingException {

        log.info("sessionBindRequested reqId:{}", sessionId);
        log.info("baseBind {}", baseBind);
        smppSessionConfiguration.setName("chy.smpp." + smppSessionConfiguration.getSystemId());
    }

    @Override
    public void sessionCreated(Long aLong, SmppServerSession smppServerSession, BaseBindResp baseBindResp) throws SmppProcessingException {
        log.info("sessionCreated reqId:{}", aLong);
        log.info("baseBindResp {}", baseBindResp);
        smppServerSession.serverReady(new TestSmppSessionHandler(smppServerSession));
    }

    @Override
    public void sessionDestroyed(Long aLong, SmppServerSession smppServerSession) {
        log.info("Session destroyed: {}", smppServerSession);
        if (smppServerSession.hasCounters()) {
            log.info("final session rx-submitSM: {}", smppServerSession.getCounters().getRxSubmitSM());
        }
        smppServerSession.destroy();
    }

    public static class TestSmppSessionHandler extends DefaultSmppSessionHandler {

        private SmppSession smppSession;

        public TestSmppSessionHandler(SmppSession session) {
            this.smppSession = session;
        }

        @Override
        public PduResponse firePduRequestReceived(PduRequest pduRequest) {
            System.out.println("firePduRequestReceived:" + smppSession);

            // mimic how long processing could take on a slower smsc
            try {
                //Thread.sleep(50);
            } catch (Exception e) {
            }

            return pduRequest.createResponse();
        }
    }

}


