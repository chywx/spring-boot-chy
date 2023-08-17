package cn.chendahai.chy.generate;

import cn.chendahai.chy.entity.RecallError;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;

import java.io.*;
import java.util.List;

public class LuckyBusReduceMoney {

    public static void main(String[] args) {


        File file = new File("C:\\Users\\cob\\Downloads\\扣款\\reduceAmount.xlsx");


        String fileName = file.getName();

        System.out.println("fileName:" + fileName);

        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if ("xlsx".equals(suffix)) {
            // 是excel文件，进行读取操作
            List<RecallError> recallErrorList;
            try {
                recallErrorList = EasyExcel.read(new FileInputStream(file), RecallError.class, new SyncReadListener())
                        .sheet(0).doReadSync();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            String writeFileName = split[0] + ".txt";
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter("C:\\Users\\cob\\Downloads\\扣款\\" + writeFileName));

                // 拼接数据
                String curl = "curl 'localhost:7081/inner/center/subChip?username=%s&amount=%s&comment=recall&password=4d681796-64a5-4f10-9617-9459a163ddf1-aefoj9x'";
                for (RecallError recallError : recallErrorList) {
                    if (recallError.getUsername() == null) {
                        continue;
                    }
                    String content = String.format(curl, recallError.getUsername(), recallError.getAmount());
//                        System.out.println(fileName + "\t" + JSONObject.toJSONString(recall) + "\t" + content);
                    System.out.println(String.format(curl, recallError.getUsername(), recallError.getAmount()));
                    printWriter.println(content);
                }

                printWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

}
