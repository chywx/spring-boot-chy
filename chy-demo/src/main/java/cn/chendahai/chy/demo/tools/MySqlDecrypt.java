package cn.chendahai.chy.demo.tools;

import com.alibaba.druid.filter.config.ConfigTools;

public class MySqlDecrypt {

    public static void main(String[] args) throws Exception {

        // mysql06
        String publicKey = "";
        String encryptMsg = "WZOADPtlifYHoZlepm1KQd4vrpVUNZieFMlbmukC4tFM1e3Aq3GoeC6di3NKs3jjFMMgMgSQZt5KgSxsbPThjw==";
        String dbpassword = ConfigTools.decrypt(publicKey, encryptMsg);
        System.out.println(dbpassword);
    }

}
