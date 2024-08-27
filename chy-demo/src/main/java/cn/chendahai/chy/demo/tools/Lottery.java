package cn.chendahai.chy.demo.tools;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class Lottery {

    @Test
    public void test2(){
        JSONObject jsonObject = JSONObject.parseObject("\t\n" +
                "{\"ng\":\"100679,100984,11212\",\"ug\":\"200679,200984,11211\",\"tz\":\"300679,11213,302206,302183,302097\",\"ke\":\"11214,400679,400984\"}");
        System.out.println(jsonObject.toJSONString());

        String ng = ",100002,100094,100288,101043,101915,102066";
        String ug = ",200002,200094,200288,201043,201915,202066";
        String tz = ",300002,300094,300288,301043,301915,302066";
        String ke = ",400002,400094,400288,401043,401915,402066";
        jsonObject.put("ng",jsonObject.getString("ng")+ng);
        jsonObject.put("ug",jsonObject.getString("ug")+ug);
        jsonObject.put("tz",jsonObject.getString("tz")+tz);
        jsonObject.put("ke",jsonObject.getString("ke")+ke);

        // {"ug":"200679,200984,11211,200002,200094,200288,201043,201915,202066","tz":"300679,11213,302206,302183,302097,300002,300094,300288,301043,301915,302066","ng":"100679,100984,11212,100002,100094,100288,101043,101915,102066","ke":"11214,400679,400984,400002,400094,400288,401043,401915,402066"}
        System.out.println(jsonObject.toJSONString());
    }

    enum Prize {
        PRIZE_1(0.1),
        PRIZE_2(0.2),
        PRIZE_3(0.7); // 其他奖品或谢谢参与

        private final double probability;

        Prize(double probability) {
            this.probability = probability;
        }

        public double getProbability() {
            return probability;
        }
    }

    public static Prize draw() {
        double randomNum = Math.random();
        System.out.println(randomNum);
        double sum = 0.0;
        for (Prize prize : Prize.values()) {
            sum += prize.getProbability();
            if (randomNum <= sum) {
                return prize;
            }
        }
        System.out.println(">>>>>>>>>");
        // 这里可以添加一个默认的奖品，比如"谢谢参与"
        return Prize.PRIZE_3;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            Prize prize = draw();
            System.out.println("恭喜您抽中了：" + prize);
        }
    }

}
