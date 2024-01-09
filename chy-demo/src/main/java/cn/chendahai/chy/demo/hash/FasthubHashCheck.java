package cn.chendahai.chy.demo.hash;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;

public class FasthubHashCheck {

    /**
     * 推送回调校验
     */
    @Test
    public void test1() {
        String str = "{\n" +
                "  \"hash\": \"571ac832d0ccbd15c467dd18d9d2807c51335917172e2fe378b1651150152cb2\",\n" +
                "  \"trx_date\": \"2024-01-08 00:56:00\",\n" +
                "  \"paybill_number\": \"504050\",\n" +
                "  \"reference_id\": \"123\",\n" +
                "  \"msisdn\": \"255615698986\",\n" +
                "  \"amount\": 500,\n" +
                "  \"operator\": \"Halopesa\",\n" +
                "  \"country\": \"Tanzania\"\n" +
                "}";
        FastHubValidationRequest fastHubValidationRequest = JSONObject.parseObject(str, FastHubValidationRequest.class);

        String hash = String.format("amount=%s&country=%s&msisdn=%s&operator=%s&paybill_number=%s&reference_id=%s&trx_date=%s",
                URLEncoder.encode(fastHubValidationRequest.getAmount().toString()),
                URLEncoder.encode(fastHubValidationRequest.getCountry()),
                URLEncoder.encode(fastHubValidationRequest.getMsisdn()),
                URLEncoder.encode(fastHubValidationRequest.getOperator()),
                URLEncoder.encode(fastHubValidationRequest.getPayBillNumber()),
                URLEncoder.encode(fastHubValidationRequest.getReferenceId()),
                URLEncoder.encode(fastHubValidationRequest.getTrxDate()));
        System.out.println(hash);
        String hashResult = EncryptUtil.hashHmac256(hash, "b5e688589a75bd8a62b1b6562e64aa20");
        System.out.println(hashResult);

    }

    @Test
    public void test2() {
//        String str = "{\n" +
//                "  \"hash\": \"bea02b8d30f9d92e397d01febb3b53f9153b6f23979ed32e298fc4fef5bde234\",\n" +
//                "  \"trx_date\": \"2024-01-05 11:15:22\",\n" +
//                "  \"company\": \"BetSure C2B\",\n" +
//                "  \"operator\": \"Halopesa\",\n" +
//                "  \"txid\": \"032037116378170\",\n" +
//                "  \"paybill_number\": \"522522\",\n" +
//                "  \"receipt\": \"2102196107\",\n" +
//                "  \"msisdn\": \"255621571153\",\n" +
//                "  \"amount\": 200,\n" +
//                "  \"reference\": \"621571153\",\n" +
//                "  \"country\": \"Tanzania\"\n" +
//                "}";

//        String str = "{\n" +
//                "  \"hash\": \"88b0d908e1429e033f46318f2bdfd8ae9c68e8824c44dd74fd2da8f90cb9fda3\",\n" +
//                "  \"trx_date\": \"2024-01-05 11:15:23\",\n" +
//                "  \"company\": \"BetSure C2B\",\n" +
//                "  \"operator\": \"Halopesa\",\n" +
//                "  \"txid\": \"847135467617644\",\n" +
//                "  \"paybill_number\": \"522522\",\n" +
//                "  \"receipt\": \"2102226971\",\n" +
//                "  \"msisdn\": \"255621316720\",\n" +
//                "  \"amount\": 500,\n" +
//                "  \"reference\": \"123\",\n" +
//                "  \"country\": \"Tanzania\"\n" +
//                "}\n";
        String str = "{\n" +
                "  \"hash\": \"15e7be5f4844b16d76b6562c8f12fc378a3dccb6cd99f187c88b5826fdb9d974\",\n" +
                "  \"trx_date\": \"2024-01-08 14:37:14\",\n" +
                "  \"company\": \"BetSure C2B\",\n" +
                "  \"operator\": \"TigoPesaTz\",\n" +
                "  \"txid\": \"282460523226660\",\n" +
                "  \"paybill_number\": \"522522\",\n" +
                "  \"receipt\": \"196817660523\",\n" +
                "  \"msisdn\": \"255658652205\",\n" +
                "  \"amount\": 500,\n" +
                "  \"reference\": \"123\",\n" +
                "  \"country\": \"Tanzania\"\n" +
                "}";
        FastHubC2bDepositCallBackRequest fastHubC2bDepositCallBackRequest = JSONObject.parseObject(str, FastHubC2bDepositCallBackRequest.class);

        String hash = String.format("amount=%s&company=%s&country=%s&msisdn=%s&operator=%s&paybill_number=%s&receipt=%s&reference=%s&trx_date=%s&txid=%s",
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getAmount().toString()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getCompany()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getCountry()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getMsisdn()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getOperator()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getPayBillNumber()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getReceipt()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getReference()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getTrxDate()),
                URLEncoder.encode(fastHubC2bDepositCallBackRequest.getTxId()));
        System.out.println(hash);
        String hashResult = EncryptUtil.hashHmac256(hash, "b5e688589a75bd8a62b1b6562e64aa20");
        System.out.println(hashResult);

    }
}

@Setter
@Getter
@ToString
class FastHubValidationRequest {
    private String hash;
    private String trxDate;
    private String payBillNumber;
    private String referenceId;
    private String msisdn;
    private BigDecimal amount;
    private String operator;
    private String country;
}

@Setter
@Getter
@ToString
class FastHubC2bDepositCallBackRequest {
    private String hash;
    private String trxDate;
    private String company;
    private String operator;
    private String txId;
    private String payBillNumber;
    private String receipt;
    private String msisdn;
    private BigDecimal amount;
    private String reference;
    private String country;
}

