package cn.chendahai.chy.generate;

import java.io.File;

public class OneTouchGameIcon {

    public static void main(String[] args) {
        File directory = new File("C:\\Users\\cob\\Pictures\\third-game-image-onetouch\\10-27-10");
        for (File file : directory.listFiles()) {
            if (file.getName().contains("default")) {
                System.out.println(file.getName());
            }
        }

    }

}
