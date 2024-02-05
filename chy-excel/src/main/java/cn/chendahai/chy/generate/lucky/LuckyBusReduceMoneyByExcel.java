package cn.chendahai.chy.generate.lucky;

import cn.chendahai.chy.entity.LuckyBusUser;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuckyBusReduceMoneyByExcel {

    public static void main(String[] args) {


        Map<Integer, String> userMap = getUserMap();
        System.out.println(userMap);


        String filePath = "C:\\Users\\cob\\Downloads\\召回";

//        File file = new File(filePath + "\\11月26日四国小游戏vip.xlsx");
//        File file = new File(filePath + "\\坦桑转盘抽奖.xlsx");
        File file = new File(filePath + "\\坦桑提现-异常订单-待审核-2024-01-26.xlsx");


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

//            Set<String> userIdSet = new HashSet<>();

            String writeFileName = "扣款-" + split[0] + ".txt";
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(filePath + "\\" + writeFileName));

                // 拼接数据
//                String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=operation&gameNamePrefix=activity-innerAddChip-\"";
                String curl = "curl 'localhost:7081/inner/center/subChip?username=%s&amount=%s&comment=recall&password=4d681796-64a5-4f10-9617-9459a163ddf1-aefoj9x'";
                for (LuckyBusUser user : userList) {
//                    if (user.getUserId() == null) {
//                        continue;
//                    }
                    if (user.getAddAmount() == null) {
                        continue;
                    }
                    if (user.getAddAmount().equals("0")) {
                        continue;
                    }
//                    userIdSet.add(user.getUserId() + "");
                    String content = String.format(curl, user.getUsername(), user.getAddAmount());
//                    String content = String.format(curl, userMap.get(user.getUserId()), user.getAddAmount());
                    System.out.println(fileName + "\t" + JSONObject.toJSONString(user) + "\t" + content);
//                    System.out.println(String.format(curl, user.getUserId(), user.getAddAmount()));
                    printWriter.println(content);
                }

                printWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(userIdSet);
//            String result = String.join(",", userIdSet);
//            System.out.println(result);

        }
    }


    public static Map<Integer, String> getUserMap() {
        try {
            Map<Integer, String> map = new HashMap<>();
            List<LuckyBusUser> list = EasyExcel.read(new FileInputStream("C:\\Users\\cob\\Downloads\\召回\\用户-11-29.xlsx"), LuckyBusUser.class, new SyncReadListener()).doReadAllSync();
            for (LuckyBusUser user : list) {
                map.put(user.getUserId(), user.getUsername());
            }
            return map;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Integer> getUsernameMap() {
        try {
            Map<String, Integer> map = new HashMap<>();
            List<LuckyBusUser> list = EasyExcel.read(new FileInputStream("C:\\Users\\cob\\Downloads\\召回\\用户-11-29.xlsx"), LuckyBusUser.class, new SyncReadListener()).doReadAllSync();
            for (LuckyBusUser user : list) {
                map.put(user.getUsername(), user.getUserId());
            }
            return map;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
