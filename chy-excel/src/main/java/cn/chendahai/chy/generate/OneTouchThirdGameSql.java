package cn.chendahai.chy.generate;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OneTouchThirdGameSql {

    public static void main(String[] args) throws IOException {

        // 标准的插入语句
//        String sql = "INSERT INTO `gaming_center`.`third_game`(`id`, `partner_name`, `partner_game_id`, `name`, `enabled`, `category`, `product`, `is_slot`, `platforms`, `limit_configs`, `supported_promotions`, `slots_bets_configs`, `self_config`, `operator_id`) VALUES (5269, 'Skywind', 5269, 'Money Trees Of Spring', 1, 'Table Games', 'General', 1, NULL, NULL, NULL, NULL, NULL, 4201);";

        String sql = "INSERT INTO `gaming_center`.`third_game`(`id`, `partner_name`, `partner_game_id`, `name`, `enabled`, `category`, `product`, `is_slot`, `platforms`, `limit_configs`, `supported_promotions`, `slots_bets_configs`, `self_config`, `operator_id`) VALUES (%s, 'OneTouch', %s, '%s', 1, 'Video Slots', 'General', 1, '[\\\"GPL_MOBILE\\\",\\\"GPL_DESKTOP\\\"]', '{\\\"NGN\\\":[{\\\"min\\\":50.0,\\\"max\\\":5000.0}],\\\"UGX\\\":[{\\\"min\\\":100.0,\\\"max\\\":10000.0}],\\\"KES\\\":[{\\\"min\\\":10.0,\\\"max\\\":1000.0}],\\\"FUN\\\":[{\\\"min\\\":10.0,\\\"max\\\":1000.0}]}', '[\\\"FREE_SPINS\\\"]', '{\\\"NGN\\\":{\\\"betSizes\\\":[50.0,100,150,200,250,300,350,400,450,500],\\\"coinValues\\\":[1.0,2,4,6,8,10]},\\\"UGX\\\":{\\\"betSizes\\\":[50.0,100,150,200,250,300,350,400,450,500],\\\"coinValues\\\":[2.0,4,8,12,16,20]},\\\"KES\\\":{\\\"betSizes\\\":[50.0,100,150,200,250,300,350,400,450,500],\\\"coinValues\\\":[0.2,0.4,0.8,1.2,1.6,2]},\\\"FUN\\\":{\\\"betSizes\\\":[50.0,100,150,200,250,300,350,400,450,500],\\\"coinValues\\\":[0.2,0.4,0.8,1.2,1.6,2]}}', NULL, 1000);";

        Integer startId = 292951;


        FileReader fileReader = new FileReader("C:\\Users\\cob\\Pictures\\third-game-image-onetouch\\10-30-18\\game.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            int indexOf = line.indexOf("\t");
            String thirdGameId = line.substring(0, indexOf).trim();
            String gameName = line.substring(indexOf).trim();
            startId++;
            // '需要转为\'
            gameName = gameName.replace("'", "\\'");
            System.out.println(String.format(sql, startId, thirdGameId, gameName));
        }
        bufferedReader.close();

    }

    @Test
    public void test1() {
        String str = "202107192\tGolden Shisa";
        int indexOf = str.indexOf("\t");
        System.out.println(str.substring(0, indexOf).trim());
        System.out.println(str.substring(indexOf).trim());

    }


}
