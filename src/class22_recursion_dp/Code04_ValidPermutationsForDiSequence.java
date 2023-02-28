package class22_recursion_dp;

// 给定一个长度为 n 的字符串 s ，其中 s[i] 是:
// D 意味着减少
// I 意味着增加
// 有效排列 是对有 n + 1 个在 [0, n]  范围内的整数的一个排列 perm ，使得对所有的 i：
// 如果 s[i] == 'D'，那么 perm[i] > perm[i+1]，以及；
// 如果 s[i] == 'I'，那么 perm[i] < perm[i+1]。
// 返回 有效排列  perm的数量 。因为答案可能很大，所以请返回你的答案对 10^9 + 7 取余。
// 测试链接 : https://leetcode.cn/problems/valid-permutations-for-di-sequence/
//dp 斜率优化
public class Code04_ValidPermutationsForDiSequence {
    public static int validPermutationForDISequence1(String s) {
        int n = s.length() + 1; //一共有n个数字
        //当前来到位置0，前面没有数字，可假设前面的数字是无穷大，则还有n个数字比它小
        return process(s, 0, n, n);
    }

    //i - 来到填的数字的位置i，前一个i-1位置上的数是X
    //less - 剩下的数字中有几个比X小
    private static int process(String s, int i, int less, int n) {
        //已经遍历完所有的数字
        if (i == n) {
            return 1;
        }
        int ways = 0;
        //i == 0，有n个数字比前面小
        // 'D' 下降 后面是更小的数字
        // nextLess 0 选了最小的
        // nextLess 1 选了次小的
        if (i == 0 || s.charAt(i - 1) == 'D') {
            for (int nextLess = 0; nextLess < less; nextLess++) {
                ways += process(s, i + 1, nextLess, n);
            }
        } else { //s.charAt(i - 1) == 'I'
            // 'I' 上升 后面是更大的数字 还有 n - i个数字，其中less个比X小
            // nextLess - less 选了> X的数字中最小的数字
            // nextLess - less + 1 选了> X的数字中次小的数字
            for (int nextLess = less; nextLess < n - i; nextLess++) {
                ways += process(s, i + 1, nextLess,n);
            }
        }
        return ways;
    }

    public static int validPermutationForDISequence2(String s) {
        int mod = 1000000007;
        int n = s.length() + 1;
        int[][] dp = new int[n + 1][n + 1]; //dp[i][less]

        for (int less = 0; less <= n; less++) {
            dp[n][less] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int less = 0; less <= n; less++) {
                if (i == 0 || s.charAt(i - 1) == 'D') {
                    for (int nextLess = 0; nextLess < less; nextLess++) {
                        dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
                    }
                } else {
                    for (int nextLess = less; nextLess < n - i; nextLess++) {
                        dp[i][less] = (dp[i][less] + dp[i + 1][nextLess]) % mod;
                    }
                }
            }
        }

        return dp[0][n];
    }

    //斜率优化 优化掉枚举行为
    public static int validPermutationForDISequence3(String s) {
        int mod = 1000000007;
        int n = s.length() + 1;

        int[][] dp = new int[n + 1][n + 1];
        for (int less = 0; less <= n; less++) {
            dp[n][less] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            if (i == 0 || s.charAt(i - 1) == 'D') {
                for (int less = 0; less <= n; less++) {
                    //dp[i][0] 后面没有数字比X更小，无法满足D的设定，因此0种方法
                    dp[i][less] = less - 1 >= 0 ? (dp[i][less - 1] + dp[i + 1][less - 1]) % mod : 0;
                }
            } else {
//                dp[i][n - i - 1] = dp[i + 1][n - i - 1];
                for (int less = n - i - 1; less >= 0; less--) {
                    dp[i][less] = less == n - i - 1 ? dp[i + 1][n - i - 1] : (dp[i][less + 1] + dp[i + 1][less]) % mod;
                }
            }
        }
        return dp[0][n];
    }
}
