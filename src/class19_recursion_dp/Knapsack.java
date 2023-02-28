package class19_recursion_dp;

//背包问题
/*
给定两个长度都为N的数组weights和values，
weights[i]和values[i]分别代表 i号物品的重量和价值。
给定一个正数bag，表示一个载重bag的袋子，
你装的物品不能超过这个重量。
返回你能装下最多的价值是多少?
 */
public class Knapsack {
    //暴力递归
    public static int maxValue1(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    //当前来到index号货物，还可以装的重量是rest，求能得到的最大value [index, ...]范围上能得到的最大值
    private static int process(int[] w, int[] v, int index, int rest) {
        if (rest == 0) {
            return 0;
        }
        if (index == w.length) {
            return 0; //已经没东西可选了
        }
        //不选
        int p1 = process(w, v, index + 1, rest);
        //选
        int p2 = 0;
        if (rest - w[index] >= 0) {
            p2 = v[index] + process(w, v, index + 1, rest - w[index]);
        }
        return Math.max(p1, p2);
    }

    //动态规划
    public static int maxValue2(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1]; //二维矩阵，行代表当前来到的货物位置，列代表还能装的重量

        // 边界值 dp[i][0] = 0; dp[N][j] = 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 1; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                if (rest - w[index] >= 0) {
                    p2 = v[index] + dp[index + 1][rest - w[index]];
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static int maxValue22(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;

        int[][] dp = new int[N + 1][bag + 1];

        for (int index = N - 1; index >= 0; index++) {
            for (int rest = 1; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = rest - w[index] >= 0 ? v[index] + dp[index + 1][rest - w[index]] : 0;
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue1(weights, values, 15));
        System.out.println(maxValue2(weights, values, 15));
    }

}
