package cn.chendahai.chy.generate.lucky;

import cn.chendahai.chy.entity.Recall;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class LuckyBusAddMoney {

    public static void main(String[] args) {

//        String rootPath = "C:\\Users\\cob\\Downloads\\0616-0622";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0621-0627";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0629-0705";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0706-0712";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0718-0724";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0726-0801";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0802-0808";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0808-0814";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0808扣款异常";
//        String rootPath = "C:\\Users\\cob\\Downloads\\0815-0821";
        String rootPath = "C:\\Users\\cob\\Downloads\\0822-0828";

        File fileDirectory = new File(rootPath);

        for (File file : fileDirectory.listFiles()) {

            String fileName = file.getName();

            System.out.println("fileName:" + fileName);

            String[] split = fileName.split("\\.");
            String suffix = split[split.length - 1];
            if ("xlsx".equals(suffix)) {
                // 是excel文件，进行读取操作
                List<Recall> recallList;
                try {
                    recallList = EasyExcel.read(new FileInputStream(file), Recall.class, new SyncReadListener())
                            .sheet(0).doReadSync();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                String writeFileName = split[0] + ".txt";
                try {
                    PrintWriter printWriter = new PrintWriter(new FileWriter(rootPath + "\\" + writeFileName));

                    // 拼接数据
                    String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=recall&gameNamePrefix=activity-innerAddChip-\"";
                    for (Recall recall : recallList) {
                        if (recall.getUserId() == null) {
                            continue;
                        }
                        String content = String.format(curl, recall.getUserId(), recall.getRewardAmount());
//                        System.out.println(fileName + "\t" + JSONObject.toJSONString(recall) + "\t" + content);
                        System.out.println(String.format(curl, recall.getUserId(), recall.getRewardAmount()));
                        printWriter.println(content);
                    }

                    printWriter.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }

        }
    }

}
