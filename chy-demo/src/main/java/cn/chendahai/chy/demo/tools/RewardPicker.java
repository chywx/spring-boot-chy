package cn.chendahai.chy.demo.tools;

import java.util.Random;

public class RewardPicker {
    private static final double PRIZE1_RATE = 0.1; // 第一等奖中奖率为10%
    private static final double PRIZE2_RATE = 0.3; // 第二等奖中奖率为30%
    private static final double PRIZE3_RATE = 0.6; // 第三等奖中奖率为60%

    private static final int TOTAL_PRIZES = 10000; // 奖品总数

    public String pickReward() {
        Random random = new Random();
        double probability = random.nextDouble(); // 生成一个0到1之间的随机数

        if (probability < PRIZE1_RATE) {
            return "一等奖";
        } else if (probability < PRIZE2_RATE) {
            return "二等奖";
        } else if (probability < PRIZE3_RATE) {
            return "三等奖";
        } else {
            return "谢谢参与";
        }
    }

    public static void main(String[] args) {
        RewardPicker picker = new RewardPicker();

        for (int i = 0; i < TOTAL_PRIZES; i++) {
            String reward = picker.pickReward();
            System.out.println("第" + (i + 1) + "个奖品：" + reward);
        }
    }
}
