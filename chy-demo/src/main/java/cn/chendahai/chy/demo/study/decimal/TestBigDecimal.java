package cn.chendahai.chy.demo.study.decimal;

import org.junit.Test;

import java.math.BigDecimal;

public class TestBigDecimal {

    @Test
    public void test1() {
        double ratio = 0.77676;

        int betAmount = 100;
        BigDecimal bigDecimal = new BigDecimal(betAmount + "").multiply(new BigDecimal(ratio)).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal);

        BigDecimal bigDecimal1 = new BigDecimal(betAmount + "").multiply(BigDecimal.ONE.subtract(new BigDecimal(ratio))).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal1);
    }

    @Test
    public void test2() {
        System.out.println(1.1 + 2.2);
    }

    @Test
    public void test3() {
        BigDecimal divide = new BigDecimal(2).divide(new BigDecimal("3"), 3, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
    }
}
