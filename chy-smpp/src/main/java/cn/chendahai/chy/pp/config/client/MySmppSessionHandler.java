package cn.chendahai.chy.pp.config.client;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;

public class MySmppSessionHandler implements com.cloudhopper.smpp.SmppSessionHandler {
    @Override
    public void fireChannelUnexpectedlyClosed() {

    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        return null;
    }

    @Override
    public void firePduRequestExpired(PduRequest pduRequest) {

    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {

    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {

    }

    @Override
    public void fireUnrecoverablePduException(UnrecoverablePduException e) {

    }

    @Override
    public void fireRecoverablePduException(RecoverablePduException e) {

    }

    @Override
    public void fireUnknownThrowable(Throwable throwable) {

    }

    @Override
    public String lookupResultMessage(int i) {
        return null;
    }

    @Override
    public String lookupTlvTagName(short i) {
        return null;
    }
}
