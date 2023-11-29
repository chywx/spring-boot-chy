package cn.chendahai.chy.generate;

import cn.chendahai.chy.entity.LuckyBusUser;
import cn.chendahai.chy.entity.Recall;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.List;

public class LuckyBusAddMoneyByExcel {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\cob\\Downloads\\召回";

//        File file = new File(filePath + "\\11月26日四国小游戏vip.xlsx");
        File file = new File(filePath + "\\肯尼亚流失大户奖励1127.xlsx");


        String fileName = file.getName();

        System.out.println("fileName:" + fileName);

        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if ("xlsx".equals(suffix)) {
            // 是excel文件，进行读取操作
            List<LuckyBusUser> userList;
            try {

//                userList = EasyExcel.read(new FileInputStream(file), LuckyBusUser.class, new SyncReadListener()).sheet(0).doReadSync();
                userList = EasyExcel.read(new FileInputStream(file), LuckyBusUser.class, new SyncReadListener()).doReadAllSync();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            String writeFileName = split[0] + ".txt";
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(filePath + "\\" + writeFileName));

                // 拼接数据
//                String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=operation&gameNamePrefix=activity-innerAddChip-\"";
                String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=operation&gameNamePrefix=activity-halloween--\"";
                for (LuckyBusUser user : userList) {
                    if (user.getUserId() == null) {
                        continue;
                    }
                    if (user.getAddAmount() == null) {
                        continue;
                    }
                    if (user.getAddAmount().equals("0")) {
                        continue;
                    }
                    String content = String.format(curl, user.getUserId(), user.getAddAmount());
                    System.out.println(fileName + "\t" + JSONObject.toJSONString(user) + "\t" + content);
//                    System.out.println(String.format(curl, user.getUserId(), user.getAddAmount()));
                    printWriter.println(content);
                }

                printWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

}
