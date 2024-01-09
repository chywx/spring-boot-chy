package cn.chendahai.chy.demo.hash;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class EncryptUtil {

//    public static void main(String[] args) {
//        String hash = String.format(FastHubConstant.DEBIT_DEPOSIT_HASH_TEMPLATE, URL.encode("500"), URL.encode("2047"),
//            URL.encode("https://casino-api.bangbet.com/api/payments/fasthub/depositDebitCallback"),
//            URL.encode("255658652205"), URL.encode("89cd596c-ebc3-4b39-9a61-cad1766882c1"),
//            URL.encode("2023-09-28 02:47:42"));
//        // String hash =
//        // "amount=100&channel=2047&callback_url=http://117.22.253.110:38086/api/payments/fasthub/depositDebitCallback&recipient=255677051151&reference_id=11111111&trx_date=2022-11-03
//        // 10:44:55";
//        // String hash1 =
//        // "amount=100&channel=2047&callback_url=http%3A%2F%2F117.22.253.110%3A38086%2Fapi%2Fpayments%2Ffasthub%2FdepositDebitCallback&recipient=255677051151&reference_id=11111111&trx_date=2022-11-03+10%3A44%3A55";
//        System.out.println(hash);
//        System.out.println(EncryptUtil.hashHmac256(hash, "Q6yJIkSpXbHhsTMyDj1W2ziZgjUB5rm7"));
//    }

    public static String hashHmac256(final String message, final String PRIVATE_KEY) {
        try {
            final Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(PRIVATE_KEY.getBytes(), "HmacSHA256"));
            return toHexString(mac.doFinal(message.getBytes()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return PRIVATE_KEY;
    }

    private static String toHexString(final byte[] bytes) {
        final Formatter formatter = new Formatter();
        for (final byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String SHA256(final String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText) {
        return SHA(strText, "SHA-512");
    }

    private static String SHA(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

}
