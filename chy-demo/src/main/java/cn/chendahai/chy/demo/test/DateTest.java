package cn.chendahai.chy.demo.test;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTest {

    @Test
    public void test1() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        System.out.println(LocalDate.now());
    }
}
