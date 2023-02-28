package class46_dp;

/*
https://leetcode.cn/problems/burst-balloons/
给定一个数组 arr，代表一排有分数的气球。每打爆一个气球都能获得分数，假设打爆气 球 的分数为 X，获得分数的规则如下:
1)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。 获得分数为 L*X*R。
2)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;如果被打爆气球的右边所有气球都已经被打爆。获得分数为 L*X。
3)如果被打爆气球的左边所有的气球都已经被打爆;如果被打爆气球的右边有没被打爆的 气球，找到离被打爆气球最近的气球，假设分数为 R;如果被打爆气球的右边所有气球都 已经 被打爆。获得分数为 X*R。
4)如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。
目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的总分，请返回能获得的最大分数。
【举例】
arr = {3,2,5}
如果先打爆3，获得3*2;再打爆2，获得2*5;最后打爆5，获得5;最后总分21
如果先打爆3，获得3*2;再打爆5，获得2*5;最后打爆2，获得2;最后总分18
如果先打爆2，获得3*2*5;再打爆3，获得3*5;最后打爆5，获得5;最后总分50
如果先打爆2，获得3*2*5;再打爆5，获得3*5;最后打爆3，获得3;最后总分48
如果先打爆5，获得2*5;再打爆3，获得3*2;最后打爆2，获得2;最后总分18
如果先打爆5，获得2*5;再打爆2，获得3*2;最后打爆3，获得3;最后总分19
返回能获得的最大分数为50
 */
public class BurstBalloons {
    //[L..R] 前提假设arr[L - 1]一定没被打爆，arr[R + 1]一定没被打爆
    public static int maxScore(int[] arr) {
        int N = arr.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        return process0(help, 1, N);
    }

    //尝试：[L, R]范围上，打爆所有气球的最大得分
    // L - 1, R + 1 永不越界，且都还没爆
    private static int process0(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L - 1] * arr[L] * arr[R + 1];
        }
        int ans = Integer.MIN_VALUE;
        //谁是最后被打爆的气球？
        //L
        ans = Math.max(ans, process0(arr, L + 1, R) + arr[L] * arr[L - 1] * arr[R + 1]);
        //R
        ans = Math.max(ans, process0(arr, L, R - 1) + arr[R] * arr[L - 1] * arr[R + 1]);
        //(L, R) 所有中间位置
        for (int i = L + 1; i < R; i++) {
            ans = Math.max(ans, process0(arr, L, i - 1) + process0(arr, i + 1, R) + arr[i] * arr[L - 1] * arr[R + 1]);
        }
        return ans;
    }

    //动态规划
    public static int maxScore1(int[] arr) {
        int N = arr.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        int[][] dp = new int[N + 2][N + 2];
        for (int i = 1; i < N + 1; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        //普便的位置[L, R]依赖dp表中的下边和左边的位置
        for (int L = N; L >= 1; L--) {
            for (int R = L + 1; R <= N; R++) {
                int ans = help[L - 1] * help[L] * help[R + 1] + dp[L + 1][R];
                ans = Math.max(ans, help[L - 1] * help[R] * help[R + 1] + dp[L][R - 1]);
                for (int i = L + 1; i < R; i++) {
                    ans = Math.max(ans, help[L - 1] * help[i] * help[R + 1] + dp[L][i - 1] + dp[i + 1][R]);
                }
                dp[L][R] = ans;
            }
        }

        return dp[1][N];
    }
}
