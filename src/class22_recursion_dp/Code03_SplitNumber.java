package class22_recursion_dp;

/*
    给定一个正数n，求n的裂开方法数，
    规定：后面的数不能比前面的数小
    比如4的裂开方法有：
    1+1+1+1、1+1+2、1+3、2+2、4
    5种，所以返回5
 */

public class Code03_SplitNumber {
    public static int ways(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n); //补一个前置已拆出的数1，假设拆的是1 + n
    }

    //上一个拆出来的数是1，接下来要拆rest，问有几种拆法
    private static int process(int pre, int rest) {
        if (rest == 0) { //拆完了，说明找到一种方法
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        int ways = 0;

        for (int first = pre; first <= rest; first++) { //上面依赖下面的
            ways += process(first, rest - first);
        }

        return ways;
    }

    public static int waysDp(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int[][] dp = new int[n + 1][n + 1]; //默认有dp[pre][rest] = 0, 当 pre > rest
        dp[0][0] = 1;
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }


        //dp[i][j]依赖于左下方的某些位置
        for (int pre = n - 1; pre > 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) { //rest == pre已经填好，rest < pre 也已经填好0
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }

        return dp[1][n];
    }

    public static int waysDp2(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            dp[i][i] = 1;
        }
        for (int pre = n - 1; pre > 0; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }

    public static void main(String[] args) {
        int num = 10;
        System.out.println(ways(num));
        System.out.println(waysDp(num));
        System.out.println(waysDp2(num));
    }
}
