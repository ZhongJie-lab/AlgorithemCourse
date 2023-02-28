package class26_matrixFastPower;

import javax.print.DocFlavor;

public class FibonacciProblem {
    public static int f1(int n) {
        if (n < 1) return 0;
        if (n == 1  || n == 2) {
            return 1;
        }

        return f1(n - 1) + f1(n - 2);
    }

    public static int f2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static int f3(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;

        int pre = 1;
        int curr = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = curr;
            curr = pre + curr;
            pre = tmp;
        }
        return curr;
    }

    public static int f4(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return 1;

        int[][] base = {
                {1, 1},
                {1, 0}
        };

        int[][] res =matrixPower(base, n - 2);

        return res[0][0] + res[1][0];
    }

    private static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }

        int[][] t = m; //矩阵的一次方
        while (p != 0) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, t);
            }
            t = multiMatrix(t, t);
            p >>= 1; //右移一位
        }
        return res;
    }

    private static int[][] multiMatrix(int[][] m1, int[][] m2) { //m1的列数和m2的行数相等
        int res[][] = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) { //m1 的行
            for (int j = 0; j < m2[0].length; j++) { //m2 的列
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }



    public static void main(String[] args) {
        int n = 6;
        System.out.println(f2(6));
        System.out.println(f3(6));
        System.out.println(f4(6));
        System.out.println(f1(6));
    }
}
