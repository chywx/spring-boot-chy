package cn.chendahai.chy.demo.tools;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 公钥加密：公钥通常用于加密数据，发送方使用接收方的公钥来加密要发送的信息。只有持有相应私钥的接收方才能解密数据。
 * 私钥加密：私钥通常用于数字签名，发送方使用自己的私钥对数据进行签名，接收方使用发送方的公钥来验证签名的真实性。
 */
public class RSACryptography {

    @Test
    public void testEncrypt() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("D:\\opt\\dbkey\\hw-prod-private-key.txt"));
        String privateKey = reader.readLine();

        String plainText = "kuG8CVzLBJ0F";
        String cipherText = ConfigTools.encrypt(privateKey, plainText);

        System.out.println(cipherText);
    }

    @Test
    public void testDecrypt() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("D:\\opt\\dbkey\\hw-prod-public-key.txt"));
        String publicKey = reader.readLine();

        String cipherText = "bF6lN6L6QUy7R7Z/YemJC+pklYiMdoMCLxtxvkovPsLrW73fM8sarIInDZKnQWpM8i0TeGzIQKJXDNLI9IvenQ==";
        String decrypt = ConfigTools.decrypt(publicKey, cipherText);
        System.out.println(decrypt);
    }

    /**
     * 生成密钥对
     *
     * @param keyLength
     * @return
     */
    public static KeyPair genKeyPair(int keyLength) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥签名-公钥验签
     */
    @Test
    public void test1() {
        String data = "kuG8CVzLBJ0F";
        String sign = sign(data, getPrivateKey("MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAo/AxDRmNdhIQwa+blKIQ3i9iBZnPjrOrUemlBzysEyhePhQHUFKaVxazs8rF7m0DxrAuxyROK75gQfIhvi0uIwIDAQABAkAAg+rDjccCDgB7e1/T0caXzDJUri0W1728Hi3kne40iTSktMuXvKcMlgEzaRu72mn2QXgJXJEnxW+jDsrI613BAiEA8GN48ZD+wbXlYm3FRZK90fhkmfm7shi4TnBmL1KKwHMCIQCulbYNoTKPA0zsGOZBBv71UrsQCstifSmZ52Hj3cbfkQIgGdC05kxJ74eXMLDVJJmTpP7l3ttA2ulosZPEhYR5vh0CIAPR7svZjRun/NTlrpQZrRZ9Gu687bpX9sMRVwj/X0yhAiBJpdUbNmCTb7n2+pgDaxDxC8lAgW8HeKJeuiWB306urQ=="));
        System.out.println(sign);

        boolean verify = verify(data, sign, getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKPwMQ0ZjXYSEMGvm5SiEN4vYgWZz46zq1HppQc8rBMoXj4UB1BSmlcWs7PKxe5tA8awLsckTiu+YEHyIb4tLiMCAwEAAQ=="));
        System.out.println(verify);
    }

    /**
     * 公钥加密，私钥解密
     */
    @Test
    public void test2() throws Exception {
        String data = "hello world";

        KeyPair keyPair = genKeyPair(512);

        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        String encryptStr = encryptRSA(data, publicKey);
        // 私钥加密
        System.out.println("加密后：" + encryptStr);
        // 公钥解密
        String originData = decryptRSA(encryptStr, privateKey);
        System.out.println("解密后：" + originData);
    }

    /**
     * 私钥加密-公钥解密 有问题，一般是公钥加密，私钥解密
     */
    @Test
    public void test3() throws Exception {
        String data = "hello world";

        KeyPair keyPair = genKeyPair(512);

        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        // 私钥加密
        String encryptStr = ConfigTools.encrypt(Base64.getEncoder().encodeToString(privateKey.getEncoded()), data);
        System.out.println("加密后：" + encryptStr);
        // 公钥解密
        String originData = ConfigTools.decrypt(Base64.getEncoder().encodeToString(publicKey.getEncoded()), encryptStr);
        System.out.println("解密后：" + originData);
    }

    /**
     * 公钥验签
     */
    @Test
    public void test4() {
        String data = "{\"token\":\"COhuTyB6raarQzoSHCZPEDWefv4yxdjp8qPKEpdci6F2PtYAUApvPHke6df12cy7lf5HuP7RsI5Iq8NhSWVTGquRGtYR9e5X8Pd7CY5Ihc4rXM0A/Jnrpi/gTCV67C8jv5DW5u3kPXwEpJaCtZ62fo/CY6Jj0v6zcUwLXFiF9e8\",\"game_id\":\"802\",\"request_uuid\":\"FC04873975058125\"}";
        String sign = "GWCstkTq4ziNVWMVXrRIEmlbM1e/H+TW7QEE0ndRuC+19PrYJ2N9wVehxjoxbKHaJTW5mEoAMNNnA0PNCPhAir7qZUi0PWVIng+hX5rtx+liQaSE5KJRwCVQ5Qfs4CwGvbcp2NAehiQwaWyV1e2ViuqrRaurcDt5iie5gw/4sYA=";
        boolean verify = verify(data, sign, getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRiMviVmz6gxU+y29UHVOagikskbQcyMzjsIhGRIONUzWp0WSUBhjSm8q3VryX1hCDwoVWdUbtaftZ1HuNqrEPYCmGWylDD8ed+Rf7w4J8SveUmH77A2yDwNx5xEwrGIofEdUq4ZR0D0DguU0lbQq0c5doDlqmQ+f4rDRQUQyNuQIDAQAB"));

        System.out.println(verify);
    }


    /**
     * 公钥加密
     *
     * @param str       需要加密的字符串
     * @param publicKey
     * @return
     */
    public static String encryptRSA(String str, PublicKey publicKey) {
        try {
            byte[] inputByte = str.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(inputByte);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param str        加密后的字符串
     * @param privateKey
     * @return
     */
    public static String decryptRSA(String str, PrivateKey privateKey) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(inputByte);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String sign(String data, PrivateKey privateKey) {
        try {
            byte[] dataBytes = data.getBytes();
            Signature signature = Signature.getInstance("SHA256withRSA");//这个根据需求填充SHA1WithRSA或SHA256WithRSA
            signature.initSign(privateKey);
            signature.update(dataBytes);
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean verify(String data, String sign, PublicKey publicKey) {
        boolean verifySignSuccess = false;
        try {
            Signature verifySign = Signature.getInstance("SHA256withRSA");
            verifySign.initVerify(publicKey);
            verifySign.update(data.getBytes());

            verifySignSuccess = verifySign.verify(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifySignSuccess;
    }

    public static PrivateKey getPrivateKey(String key) {
//        return getPrivateKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(key));
        return getPrivateKey(Base64.getDecoder().decode(key));
    }

    public static PrivateKey getPrivateKey(byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey getPublicKey(String key) {
//        return getPublicKey(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(key));
        return getPublicKey(Base64.getDecoder().decode(key));
    }

    public static PublicKey getPublicKey(byte[] keyBytes) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
