package cn.chendahai.chy.pp.config.server;

import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppSession;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySmppServerSessionHandler implements SmppServerHandler {
    @Override
    public void sessionBindRequested(Long aLong, SmppSessionConfiguration smppSessionConfiguration, BaseBind baseBind) throws SmppProcessingException {
        log.info("sessionBindRequested");
    }

    @Override
    public void sessionCreated(Long aLong, SmppServerSession smppServerSession, BaseBindResp baseBindResp) throws SmppProcessingException {
        DefaultSmppSession defaultSmppSession = (DefaultSmppSession) smppServerSession;
//        defaultSmppSession.serverReady(defaultSmppSession);
        log.info("sessionCreated");
    }

    @Override
    public void sessionDestroyed(Long aLong, SmppServerSession smppServerSession) {
        log.info("sessionDestroyed");
    }
}
