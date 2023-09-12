package cn.chendahai.chy.demo.tools;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSACryptography {

    public static void main(String[] args) throws Exception {
        String data = "hello world";

        KeyPair keyPair = genKeyPair(512);

        // 获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        // 获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));


        String encrypt = ConfigTools.encrypt(Base64.getEncoder().encodeToString(privateKey.getEncoded()), data);
        System.out.println(encrypt);

        String decrypt = ConfigTools.decrypt(Base64.getEncoder().encodeToString(publicKey.getEncoded()), encrypt);
        System.out.println(decrypt);


    }

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


    @Test
    public void test1() {
        String data = "kuG8CVzLBJ0F";
        String sign = sign(data, getPrivateKey("MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAo/AxDRmNdhIQwa+blKIQ3i9iBZnPjrOrUemlBzysEyhePhQHUFKaVxazs8rF7m0DxrAuxyROK75gQfIhvi0uIwIDAQABAkAAg+rDjccCDgB7e1/T0caXzDJUri0W1728Hi3kne40iTSktMuXvKcMlgEzaRu72mn2QXgJXJEnxW+jDsrI613BAiEA8GN48ZD+wbXlYm3FRZK90fhkmfm7shi4TnBmL1KKwHMCIQCulbYNoTKPA0zsGOZBBv71UrsQCstifSmZ52Hj3cbfkQIgGdC05kxJ74eXMLDVJJmTpP7l3ttA2ulosZPEhYR5vh0CIAPR7svZjRun/NTlrpQZrRZ9Gu687bpX9sMRVwj/X0yhAiBJpdUbNmCTb7n2+pgDaxDxC8lAgW8HeKJeuiWB306urQ=="));
        System.out.println(sign);

        boolean verify = verify(data, sign, getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKPwMQ0ZjXYSEMGvm5SiEN4vYgWZz46zq1HppQc8rBMoXj4UB1BSmlcWs7PKxe5tA8awLsckTiu+YEHyIb4tLiMCAwEAAQ=="));
        System.out.println(verify);
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
