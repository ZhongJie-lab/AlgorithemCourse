package class26_matrixFastPower;

/*
    第一年农场有1只成熟的母牛A，往后的每年：
    1）每一只成熟的母牛都会生一只母牛
    2）每一只新出生的母牛都在出生的第三年成熟
    3）每一只母牛永远不会死
    返回N年后牛的数量
 */
public class CattleProblem {
    public static int c1(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    public static int c2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 4; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 3];
        }
        return dp[n];
    }

    public static int c3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 }
        };

        int[][] res =matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
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
        int[][] res = new int[m1.length][m2[0].length];

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        int n = 10;
        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
    }
}
