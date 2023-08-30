package cn.chendahai.chy.demo.tools;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
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

}
