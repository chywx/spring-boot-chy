//package cn.chendahai.chy.test.config.client;
//
//import org.jsmpp.InvalidResponseException;
//import org.jsmpp.PDUException;
//import org.jsmpp.bean.*;
//import org.jsmpp.extra.NegativeResponseException;
//import org.jsmpp.extra.ResponseTimeoutException;
//import org.jsmpp.session.BindParameter;
//import org.jsmpp.session.SMPPSession;
//import org.jsmpp.util.AbsoluteTimeFormatter;
//import org.jsmpp.util.TimeFormatter;
//
//import java.io.IOException;
//
//public class SMPPClientExample {
//    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
//
//    public static void main(String[] args) {
//        String host = "your_smpp_server_host";
//        int port = 2775; // your SMPP server port
//        String systemId = "your_system_id";
//        String password = "your_password";
//
//        try {
//            SMPPSession session = new SMPPSession();
//            session.connectAndBind(host, port, new BindParameter(BindType.BIND_TX, systemId, password, null, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
//
//            SubmitSm submitSm = new SubmitSm();
//            submitSm.setSourceAddr("sender_number");
//            submitSm.setDestAddress("recipient_number");
//            submitSm.setDataCoding((byte) GeneralDataCoding.ALPHA_DEFAULT.value());
////            submitSm.setDataCoding(GeneralDataCoding.DEFAULT.toByte());
//            submitSm.setShortMessage("Hello, World!".getBytes());
//
//            try {
//                String messageId = session.submitShortMessage("CMT", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, submitSm);
//                System.out.println("Message submitted, message_id is " + messageId);
//            } catch (PDUException e) {
//                // Invalid PDU parameter
//            } catch (ResponseTimeoutException e) {
//                // Response timeout
//            } catch (InvalidResponseException e) {
//                // Invalid response
//            } catch (NegativeResponseException e) {
//                // Negative response
//            } catch (IOException e) {
//                // IO error occurred
//            }
//
//            session.unbindAndClose();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
