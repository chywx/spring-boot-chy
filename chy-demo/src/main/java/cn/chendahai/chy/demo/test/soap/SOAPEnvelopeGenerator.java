package cn.chendahai.chy.demo.test.soap;

import javax.xml.soap.*;

public class SOAPEnvelopeGenerator {

    public static void main(String[] args) throws SOAPException {
        // Create a SOAPMessage object
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Create the SOAP Envelope
        SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();


        // Set namespaces for SOAP envelope and body
        envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
        envelope.addNamespaceDeclaration("ws", "http://ws.merchant.vmoney.viettel.com/");

        // Create the SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement haloPesaServiceAPI = soapBody.addChildElement("HaloPesaServiceAPI", "ws");

        // Create the request element
        SOAPElement request = haloPesaServiceAPI.addChildElement("request");

        // Add child elements to the request
        addElement(request, "requestid", "AX154329405");
        addElement(request, "username", "xxx");
        addElement(request, "password", "xxx");
        addElement(request, "request_time", "20180601092349");
        addElement(request, "amount", "1000");
        addElement(request, "sender_msisdn", "255623333666");
        addElement(request, "beneficiary_accountid", "100006");
        addElement(request, "referenceid", "AX154329405");
        addElement(request, "function_code", "ASYNC_PAYMENT_USSD_PUSH");
        addElement(request, "check_sum", "b875460229adc88cef4bd9904b0b06ba4b2cb4");

        // Save or send the SOAPMessage
        try {
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("Content-Type", "text/xml");
            soapMessage.writeTo(System.out); // Output to console for demonstration


            // Replace with a stream for actual transmission
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addElement(SOAPElement parent, String name, String value) throws SOAPException {
        SOAPElement element = parent.addChildElement(name);
        element.addTextNode(value);
    }

}
