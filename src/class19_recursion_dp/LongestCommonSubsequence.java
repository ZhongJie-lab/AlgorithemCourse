package class19_recursion_dp;

/*
给定两个字符串str1和str2，
返回这两个字符串的最长公共子序列长度

比如 ： str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
最长公共子序列是“123456”，所以返回长度6
 */
public class LongestCommonSubsequence {
    //暴力递归
    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        return process(str1, str2, str1.length - 1, str2.length - 1);
    }

    // str1[0...i] 和 str2[0...j] 这个范围上的最长公共子序列
    // 以i， j结尾的
    private static int process(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[i] == str2[i] ? 1 : 0;
        } else if (i == 0) {
            if(str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, str2, i, j - 1);
            }
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process(str1, str2, i - 1, j);
            }
        } else {
            int p1 = 0;
            if (str1[i] == str2[j]) {
                p1 =  1 + process(str1, str2, i - 1, j - 1);
            }
            int p2 = process(str1, str2, i - 1, j);
            int p3 = process(str1, str2, i, j - 1);
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence11(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        return process11(str1, str2, str1.length - 1, str2.length - 1);
    }

    private static int process11(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[i] == str2[i] ? 1 : 0;
        } else if (i == 0) {
            return str1[i] == str2[j] ? 1 : process11(str1, str2, i, j - 1);
        } else if (j == 0) {
            return str1[i] == str2[i] ? 1 : process11(str1, str2, i - 1, j);
        } else {
            int p1 = 0;
            if (str1[i] == str2[j]) {
                p1 = process11(str1, str2, i - 1, j - 1) + 1;
            }
            int p2 = process11(str1, str2, i - 1, j);
            int p3 = process11(str1, str2, i, j - 1);
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    //动态规划 - 样本尝试模型
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];

        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                int p2 = dp[i - 1][j];
                int p3 = dp[i][j - 1];
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];
    }

    public static int longestCommonSubsequence22(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int N = str1.length;
        int M = str2.length;

        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (str1[i] == str2[j]) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
            }
        }
        return dp[N - 1][M - 1];
    }
}
