package cn.chendahai.chy.generate.lucky;

import cn.chendahai.chy.entity.LuckyBusUser;
import cn.chendahai.chy.entity.UserBasic;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuckyBusAddMoneyByUsername {

    public static void main(String[] args) {

        String filePath = "C:\\Users\\cob\\Downloads\\召回";

        Map<String, Integer> userMap = getUserMap();


        File file = new File(filePath + "\\paybill2024-01-09之前.xlsx");


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
                String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=operation&gameNamePrefix=activity-halloween-\"";

                List<LuckyBusUser> luckyBusUsers = new ArrayList<>();
                List<LuckyBusUser> luckyBusUsersRegister = new ArrayList<>();

                for (LuckyBusUser user : userList) {
                    if (StringUtils.isEmpty(user.getUsername())) {
                        continue;
                    }
                    Integer userId = userMap.get(user.getUsername());
                    if (userId == null) {
                        luckyBusUsers.add(user);
                        continue;
                    }
                    if (true) {
//                        continue;
                    }
                    user.setUserId(userId);
                    if (user.getAddAmount() == null) {
                        continue;
                    }
                    if (user.getAddAmount().equals("0")) {
                        continue;
                    }
                    luckyBusUsersRegister.add(user);
                    String content = String.format(curl, user.getUserId(), user.getAddAmount());
                    System.out.println(fileName + "\t" + JSONObject.toJSONString(user) + "\t" + content);
//                    System.out.println(String.format(curl, user.getUserId(), user.getAddAmount()));
                    printWriter.println(content);
                }

                System.out.println(luckyBusUsers.size());
                System.out.println(luckyBusUsers);

                for (LuckyBusUser luckyBusUser : luckyBusUsers) {
                    System.out.println(luckyBusUser.getUsername() + "\t" + luckyBusUser.getAddAmount());
                }

                System.out.println(">>>>>>>register");
                for (LuckyBusUser user : luckyBusUsersRegister) {
                    System.out.println(user.getUsername() + "\t" + user.getAddAmount());
                }

                printWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public static Map<String, Integer> getUserMap() {
        try {
            Map<String, Integer> map = new HashMap<>();
            List<UserBasic> list = EasyExcel.read(new FileInputStream("C:\\Users\\cob\\Downloads\\召回\\用户-01-18.xlsx"), UserBasic.class, new SyncReadListener()).doReadAllSync();
            for (UserBasic user : list) {
                map.put(user.getUsername(), user.getUserId());
            }
            return map;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
