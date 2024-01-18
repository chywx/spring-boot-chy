package cn.chendahai.chy.generate.lucky;

import cn.chendahai.chy.entity.Recall;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LuckyBusAddMoneyByGames {

    public static void main(String[] args) {

        String rootPath = "C:\\Users\\cob\\Downloads";

        List<String> countryList = new ArrayList<String>() {{
            add("default");
            add("gh");
            add("ke");
            add("ng");
            add("ug");
        }};

        String filePath = "AviatorPlus.xlsx";
        File file = new File(rootPath + "\\" + filePath);


        String fileName = file.getName();

        System.out.println("fileName:" + fileName);

        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        if ("xlsx".equals(suffix)) {
            // 是excel文件，进行读取操作
            List<Recall> recallList;
            try {

                for (int i = 1; i < countryList.size(); i++) {
                    System.out.println(">>>>>>>>>>>>>i" + i);
                    recallList = EasyExcel.read(new FileInputStream(file), Recall.class, new SyncReadListener())
                            .sheet(i).doReadSync();

                    String writeFileName = split[0] + "-" + countryList.get(i) + ".txt";
                    PrintWriter printWriter = new PrintWriter(new FileWriter(rootPath + "\\" + writeFileName));

                    // 拼接数据
                    String curl = "curl \"localhost:7081/inner/center/addChipById?ids=%s&amount=%s&inOrder=1&payType=system&comment=recall&gameNamePrefix=activity-innerAddChip-\"";
                    for (Recall recall : recallList) {
                        if (recall.getUserId() == null) {
                            continue;
                        }
                        String content = String.format(curl, recall.getUserId(), recall.getGameAmount());
                        System.out.println(String.format(curl, recall.getUserId(), recall.getGameAmount()));
                        printWriter.println(content);
                    }

                    printWriter.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }

}
