package cn.chendahai.chy.generate;

import java.io.*;

public class SWThirdGameSql {

    public static void main(String[] args) throws IOException {

        // 标准的插入语句
//        String sql = "INSERT INTO `gaming_center`.`third_game`(`id`, `partner_name`, `partner_game_id`, `name`, `enabled`, `category`, `product`, `is_slot`, `platforms`, `limit_configs`, `supported_promotions`, `slots_bets_configs`, `self_config`, `operator_id`) VALUES (5269, 'Skywind', 5269, 'Money Trees Of Spring', 1, 'Table Games', 'General', 1, NULL, NULL, NULL, NULL, NULL, 4201);";

        String sql = "INSERT INTO `gaming_center`.`third_game`(`id`, `partner_name`, `partner_game_id`, `name`, `enabled`, `category`, `product`, `is_slot`, `platforms`, `limit_configs`, `supported_promotions`, `slots_bets_configs`, `self_config`, `operator_id`) VALUES (%s, 'Skywind', %s, '%s', 1, 'Table Games', 'General', 1, NULL, NULL, NULL, NULL, NULL, 4201);";

        Integer startId = 5269;


        FileReader fileReader = new FileReader("C:\\Users\\cob\\Downloads\\SW新游戏.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String gameName = line;
            startId++;
            // 去除括号
            gameName = gameName.trim();
            // '需要转为\'
            gameName = gameName.replace("'", "\\'");
//            System.out.println(startId + "\t" + gameName);
            System.out.println(String.format(sql, startId, startId, gameName));
        }
        bufferedReader.close();

    }

}
