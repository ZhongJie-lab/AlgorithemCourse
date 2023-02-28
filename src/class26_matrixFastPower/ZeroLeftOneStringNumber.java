package class26_matrixFastPower;

/*
给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
返回有多少达标的字符串
 */
public class ZeroLeftOneStringNumber {
    public static int getNum1(int n) {
        if (n < 1) return 0;

        return process(1, n);
    }

    //从位置i开始算 i...有几种达标的，i位置前面的假设是1
    private static int process(int i, int n) {
        if (i == n) return 1;

        if (i == n - 1) return 2;

        // i位置放1，
        // i位置放0，意味着i + 1 位置必须放1，从i + 2位置开始继续尝试
        return process(i + 1, n) + process(i + 2, n);
    }

    public static int getNum2(int n) {
        if (n < 1) return 0;
        if (n == 1) return 1;

        int[] dp = new int[n + 1];
        dp[n] = 1;
        dp[n - 1] = 2;
        for (int i = n - 2; i > 0; i--) {
            dp[i] = dp[i + 1] + dp[i + 2];
        }
        return dp[1];
    }

    public static int getNum3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;

        int[][] base = {
                { 1, 1 },
                { 1, 0 }
        };
        int[][] res = matrixPower(base, n - 2);

        return 2 * res[0][0] + res[1][0];
    }

    private static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] t = m;

        while (p != 0) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, t);
            }
            t = multiMatrix(t, t);
            p >>= 1;
        }

        return res;
    }

    private static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int N = m1.length;
        int M = m2[0].length;
        int T = m2.length;
        int[][] res = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < T; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int n = 7;
        System.out.println(getNum1(n));
        System.out.println(getNum2(n));
        System.out.println(getNum3(n));
    }
}
