package cn.chendahai.chy.demo.tools;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
使用IOUtils
 */
public class IOTest {
    public static void main(String[] args) throws IOException {
        String str = IOUtils.toString(new FileInputStream("C:\\Users\\cob\\Downloads\\github-recovery-codes.txt"), StandardCharsets.UTF_8);
        System.out.println(str);
    }
}
