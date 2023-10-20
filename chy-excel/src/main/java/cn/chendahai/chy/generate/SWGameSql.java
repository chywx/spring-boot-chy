package cn.chendahai.chy.generate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SWGameSql {

    public static void main(String[] args) throws IOException {

        // 标准的插入语句
//        INSERT INTO `gaming_center`.`game`(`id`, `name`, `avatar`, `resource`, `cts`, `uts`, `version`, `version_time`, `to_all`, `version_limit`, `status`, `unlock_level`, `unlock_chip`, `unlock_props`, `lock`, `base_jackpot`, `big_jackpot`, `service_pay`, `category_id`, `currency`, `description`, `client_template`, `ext1`, `ext2`, `ext3`, `seq`, `socket_hosts`, `service_status`, `service_notice`, `self_tax_rate`, `http_hosts`, `is_pick`, `pick_picture`, `is_third`, `third_game_id`, `new_game`, `hot_game`, `auth`, `is_in_leisure`, `game_type_for_client`, `init_jackpot`, `superscript`, `marquee_icon`, `brief`, `third_channel`, `new_game_type_for_client`, `large_cover`, `share_authority`) VALUES (4965, 'Dragon Gong', NULL, NULL, '2021-08-03 10:15:14', '2023-03-22 10:40:51', NULL, NULL, 1, '[{\"clientVersions\":[\"1.0.0\"],\"version\":\"1.0.0\"}]', 1, 1, NULL, NULL, 1, NULL, NULL, NULL, 7, 'chip', NULL, 'Dragon Gong', 'https://www.hw.luckygames.team/casino/images/208game/DragonGong_326x194.jpg', NULL, 'Dragon Gong', 99999, NULL, 1, NULL, 0.000000, NULL, 0, 'https://www.hw.luckygames.team/casino/images/game_icon_128/one.jpg', 1, 4965, 0, 0, 0, 0, 0, 200000.00, NULL, 'https://www.hw.luckygames.team/casino/images/home/one.png', '', 1, 6, NULL, 0);

        String sql = "INSERT INTO `gaming_center`.`game`(`id`, `name`, `avatar`, `resource`, `cts`, `uts`, `version`, `version_time`, `to_all`, `version_limit`, `status`, `unlock_level`, `unlock_chip`, `unlock_props`, `lock`, `base_jackpot`, `big_jackpot`, `service_pay`, `category_id`, `currency`, `description`, `client_template`, `ext1`, `ext2`, `ext3`, `seq`, `socket_hosts`, `service_status`, `service_notice`, `self_tax_rate`, `http_hosts`, `is_pick`, `pick_picture`, `is_third`, `third_game_id`, `new_game`, `hot_game`, `auth`, `is_in_leisure`, `game_type_for_client`, `init_jackpot`, `superscript`, `marquee_icon`, `brief`, `third_channel`, `new_game_type_for_client`, `large_cover`, `share_authority`) VALUES (4965, 'Dragon Gong', NULL, NULL, '2021-08-03 10:15:14', '2023-03-22 10:40:51', NULL, NULL, 1, '[{\\\"clientVersions\\\":[\\\"1.0.0\\\"],\\\"version\\\":\\\"1.0.0\\\"}]', 1, 1, NULL, NULL, 1, NULL, NULL, NULL, 7, 'chip', NULL, 'Dragon Gong', 'https://www.hw.luckygames.team/casino/images/208game/DragonGong_326x194.jpg', NULL, 'Dragon Gong', 99999, NULL, 1, NULL, 0.000000, NULL, 0, 'https://www.hw.luckygames.team/casino/images/game_icon_128/one.jpg', 1, 4965, 0, 0, 0, 0, 0, 200000.00, NULL, 'https://www.hw.luckygames.team/casino/images/home/one.png', '', 1, 6, NULL, 0);";

        Integer startId = 6000001;


        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        FileReader fileReader = new FileReader("C:\\Users\\cob\\Downloads\\SW新游戏20230711.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String gameName = line;
            // 去除括号
            gameName = gameName.trim();

            String[] split = gameName.split("=");
            String gameId = split[0];
            gameName = split[1];

            // '需要转为\'
            gameName = gameName.replace("'", "\\'");
//            System.out.println(startId + "\t" + gameName);
//            System.out.println(String.format(sql, startId, startId, gameName));

            map.put(gameId, gameName);

        }
        bufferedReader.close();

        System.out.println(map);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String thirdGameId = entry.getKey();
            String thirdGameName = entry.getValue();

        }

    }

}
