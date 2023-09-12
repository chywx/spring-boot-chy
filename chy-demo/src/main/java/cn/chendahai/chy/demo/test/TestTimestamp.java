package cn.chendahai.chy.demo.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TestTimestamp {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\cob\\Desktop\\temp-chy-11.txt");
//        File file = new File("C:\\Users\\cob\\Desktop\\temp-chy-10.txt");


        Map<String, Integer> map = new HashMap<>();

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {

                if (line.length() > 10 && line.contains("-------")) {
//                    System.out.println(line);
                    line = line.substring(line.lastIndexOf(":") + 1);
                    System.out.println(line);

                    Integer orDefault = map.getOrDefault(line, 0);
                    map.put(line, orDefault + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(map);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry);
            }

        }
    }

}
