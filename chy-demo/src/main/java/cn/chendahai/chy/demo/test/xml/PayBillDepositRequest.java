package cn.chendahai.chy.demo.test.xml;

import cn.chendahai.chy.demo.tools.XmlUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("COMMAND")
public class PayBillDepositRequest {

    public static void main(String[] args) {
        PayBillDepositRequest payBillDepositRequest = new PayBillDepositRequest();
        payBillDepositRequest.setType("SYNC_BILLPAY_REQUEST");
        payBillDepositRequest.setTxnId("txn_123");
        payBillDepositRequest.setMsisdn("255713123999");
        payBillDepositRequest.setAmount("1000");
        payBillDepositRequest.setCompanyName("504050");
        payBillDepositRequest.setCustomerReferenceId("123");
        payBillDepositRequest.setSenderName("chy");


        String xml = XmlUtil.beanToXml(payBillDepositRequest);
        System.out.println(xml);
    }

    @XStreamAlias("TYPE")
    private String type;

    @XStreamAlias("TXNID")
    private String txnId;

    @XStreamAlias("MSISDN")
    private String msisdn;

    @XStreamAlias("AMOUNT")
    private String amount;

    @XStreamAlias("COMPANYNAME")
    private String companyName;

    @XStreamAlias("CUSTOMERREFERENCEID")
    private String customerReferenceId;

    @XStreamAlias("SENDERNAME")
    private String senderName;

}
