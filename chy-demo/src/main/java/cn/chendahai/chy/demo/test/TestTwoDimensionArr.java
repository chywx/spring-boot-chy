package cn.chendahai.chy.demo.test;

public class TestTwoDimensionArr {

    public static void main(String[] args) {
        // 定义一个两行三列的二维数组并赋值
        int[][] nums = {{1, 2, 3}, {4, 5, 6}};
        System.out.println(nums);

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                System.out.print(nums[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
