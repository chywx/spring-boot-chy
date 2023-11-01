package cn.chendahai.chy.generate;

import java.io.File;

public class OneTouchGameIcon {

    public static void main(String[] args) {
        File directory = new File("C:\\Users\\cob\\Pictures\\third-game-image-onetouch\\11-01-end-11");
        for (File file : directory.listFiles()) {
            if (file.getName().contains("326x194")) {
                System.out.println(file.getName());
            }
        }

    }

}
