package class48_dp_outcome;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
public class MinimumInsertionStepsToMakeAStringPalindrome {
    //范围尝试模型
    public static int minInsertions(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int N = s.length();
        int[][] dp = new int[N][N];

        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 0 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        return dp[0][N - 1];
    }

    //返回其中一种结果
    public static String minInsertionsOneWay(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int N = s.length();
        int[][] dp = new int[N][N];

        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 0 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        //双指针
        //原字符串
        int L = 0, R = N - 1;
        char[] ans = new char[N + dp[L][R]];
        int l = 0, r = ans.length - 1; //答案之一的回文串

        while (L < R) {
            if (dp[L][R - 1] ==  dp[L][R] - 1) { //答案来自于左
                ans[l++] = s.charAt(R);
                ans[r--] = s.charAt(R--);
            } else if (dp[L + 1][R] == dp[L][R] - 1) { //答案来自于下方
                ans[l++] = s.charAt(L);
                ans[r--] = s.charAt(L++);
            } else { //答案来自于左下方  dp[L + 1][R - 1] = dp[L][R]
                ans[l++] = s.charAt(L++);
                ans[r--] = s.charAt(R--);
            }
        }
        if (L == R) { //aba
            ans[l] = s.charAt(L);
        } //eles L > R 比如aa

        return String.valueOf(ans);
    }

    public static List<String> minInsertionsWays(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            int N = s.length();
            int[][] dp = new int[N][N];

            for (int i = 0; i < N - 1; i++) {
                dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 0 : 1;
            }
            for (int i = N - 2; i >= 0; i--) {
                for (int j = i + 2; j < N; j++) {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = Math.min(dp[i + 1][j - 1], dp[i][j]);
                    }
                }
            }

            int M = N + dp[0][N - 1];
            char[] path = new char[M];
            process(s, dp, 0, N - 1, 0, M - 1, path, ans);

        }
        return ans;
    }

    private static void process(String s, int[][] dp, int L, int R, int l, int r, char[] path, List<String> ans) {
        if (L >= R) { //收集答案
            if (L == R) {
                path[l] = s.charAt(L);
            }
            ans.add(String.valueOf(path));
            return;
        }

        if (dp[L][R - 1] == dp[L][R] - 1) {
            path[l] = s.charAt(R);
            path[r] = s.charAt(R);
            process(s, dp, L, R - 1, l + 1, r - 1, path, ans);
        }
        if (dp[L + 1][R] == dp[L][R] - 1) {
            path[l] = s.charAt(L);
            path[r] = s.charAt(L);
            process(s, dp, L + 1, R, l + 1, r - 1, path, ans);
        }
        //L == R - 1， L和R中间没有字符了， L挨着R
        if (s.charAt(L) == s.charAt(R) && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) {
            path[l] = s.charAt(L);
            path[r] = s.charAt(R);
            process(s, dp, L + 1, R - 1, l + 1, r - 1, path, ans);
        }
    }

    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果");
        s = "mbadm";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        System.out.println("本题第二问，返回所有结果");

        s = "mbadm";
        ans3 = minInsertionsWays(s);
        ans3.forEach(System.out::println);

        System.out.println("-------------------------------------");
        s = "leetcode";
        ans3 = minInsertionsWays(s);
        ans3.forEach(System.out::println);
    }
}
