package class20_recursion_dp;

//https://leetcode.cn/problems/longest-palindromic-subsequence/
/*
给定一个字符串str，返回这个字符串的最长回文子序列长度
比如 ： str = “a12b3c43def2ghi1kpm”
最长回文子序列是“1234321”或者“123c321”，返回长度7
 */
public class PalindromeSubsequence {
    //解法一：范围尝试模型
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] strs = s.toCharArray();
        return process(strs, 0, strs.length - 1);
    }


    //str[L...R]最长回文子序列
    private int process(char[] strs, int L, int R) {
        if (L == R) {
            return 1;
        }
        //这个base case 可以不补
        if (L == R - 1) {
            return strs[L] == strs[R] ? 2 : 1;
        }
        int p1 = process(strs, L + 1, R - 1); //不以L开头，不以R结尾
        int p2 = process(strs, L, R - 1); //以L开头，不以R结尾
        int p3 = process(strs, L + 1, R); //不以L开头，以R结尾
        int p4 = strs[L] == strs[R] ? 2 + process(strs, L + 1, R - 1) : 0; //以L开头，以R结尾

        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] strs = s.toCharArray();
        int N = strs.length;


        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = strs[i] == strs[i + 1] ? 2 : 1;
        }
        //两条对角线填完，然后，根据依赖关系，从下往上，从左往右边
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
//               int p1 = dp[L + 1][R - 1]; //p1 肯定比不过 p2 p3 p4
//               int p2 = dp[L][R - 1];
//               int p3 = dp[L + 1][R];
//               int p4 = strs[L] == strs[R] ? 2 + dp[L + 1][R - 1] : 0;

               dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
               if (strs[L] == strs[R]) {
                   dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
               }
            }
        }

        return dp[0][N - 1];
    }

    //解法二：原串和逆序串的最长公共子序列 - 样本尝试模型
}
