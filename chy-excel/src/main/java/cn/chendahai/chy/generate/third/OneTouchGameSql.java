package cn.chendahai.chy.generate.third;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class OneTouchGameSql {

    public static void main(String[] args) throws IOException {

        // 标准的插入语句
//        INSERT INTO `gaming_center`.`game`(`id`, `name`, `avatar`, `resource`, `cts`, `uts`, `version`, `version_time`, `to_all`, `version_limit`, `status`, `unlock_level`, `unlock_chip`, `unlock_props`, `lock`, `base_jackpot`, `big_jackpot`, `service_pay`, `category_id`, `currency`, `description`, `client_template`, `ext1`, `ext2`, `ext3`, `seq`, `socket_hosts`, `service_status`, `service_notice`, `self_tax_rate`, `http_hosts`, `is_pick`, `pick_picture`, `is_third`, `third_game_id`, `new_game`, `hot_game`, `auth`, `is_in_leisure`, `game_type_for_client`, `init_jackpot`, `superscript`, `marquee_icon`, `brief`, `third_channel`, `new_game_type_for_client`, `large_cover`, `share_authority`) VALUES (5000191, 'Stone Gaze', NULL, NULL, '2023-10-23 14:44:08', '2023-10-27 10:47:51', NULL, NULL, 1, '', 1, 1, NULL, NULL, 1, NULL, NULL, NULL, 7, 'chip', NULL, '', 'https://www.hw.luckygames.team/casino/images/app/OT_StoneGaze_default.jpg', NULL, 'Stone Gaze', 11, NULL, 1, NULL, 0.000000, NULL, 0, 'https://www.hw.luckygames.team/casino/images/game_icon_128/one.jpg', 1, 292951, 1, 0, 1, 0, 0, NULL, NULL, 'https://www.hw.luckygames.team/casino/images/home/one.png', NULL, 3, 5, NULL, 0);

        String sql = "INSERT INTO `gaming_center`.`game`(`id`, `name`, `avatar`, `resource`, `cts`, `uts`, `version`, `version_time`, `to_all`, `version_limit`, `status`, `unlock_level`, `unlock_chip`, `unlock_props`, `lock`, `base_jackpot`, `big_jackpot`, `service_pay`, `category_id`, `currency`, `description`, `client_template`, `ext1`, `ext2`, `ext3`, `seq`, `socket_hosts`, `service_status`, `service_notice`, `self_tax_rate`, `http_hosts`, `is_pick`, `pick_picture`, `is_third`, `third_game_id`, `new_game`, `hot_game`, `auth`, `is_in_leisure`, `game_type_for_client`, `init_jackpot`, `superscript`, `marquee_icon`, `brief`, `third_channel`, `new_game_type_for_client`, `large_cover`, `share_authority`) VALUES (%s, '%s', NULL, NULL, '2023-10-23 14:44:08', '2023-10-27 10:47:51', NULL, NULL, 1, '', 1, 1, NULL, NULL, 1, NULL, NULL, NULL, 7, 'chip', NULL, '', 'https://www.hw.luckygames.team/casino/images/app/', NULL, '%s', 11, NULL, 1, NULL, 0.000000, NULL, 0, 'https://www.hw.luckygames.team/casino/images/game_icon_128/one.jpg', 1, %s, 1, 0, 1, 0, 0, NULL, NULL, 'https://www.hw.luckygames.team/casino/images/home/one.png', NULL, 3, 5, NULL, 0);";

        int startId = 5000371;


        FileReader fileReader = new FileReader("C:\\Users\\cob\\Pictures\\third-game-image-onetouch\\11-01-end-11\\z-game-onetouch.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            startId += 10;
            String gameName = line;
            // 去除括号
            gameName = gameName.trim();
            String[] split = gameName.split("=");
            String gameId = split[0];
            gameName = split[1].trim();

            // '需要转为\'
            gameName = gameName.replace("'", "\\'");

//            System.out.println(gameId + "\t" + gameName);
//            System.out.println(startId + "\t" + gameName);
            System.out.println(String.format(sql, startId + "", gameName, gameName, gameId));


        }
        bufferedReader.close();


    }

}
