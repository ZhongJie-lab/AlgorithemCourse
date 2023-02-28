package class18_recursion_dp;

/*
假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
如果机器人来到1位置，那么下一步只能往右来到2位置；
如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
如果机器人来到中间位置，那么下一步可以往左走或者往右走；
规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
给定四个参数 N、M、K、P，返回方法数。
 */
public class RobotWalk {
    //暴力递归
    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start > N || aim < 1 || aim > N || K < 1) return -1;

        return process(start, K, aim, N);
    }

    //在curr位置，走rest步，目标是aim，要几种走法
    private static int process(int curr, int rest, int aim, int N) {
        if (rest == 0) {
            return curr == aim ? 1 : 0;
        }
        if (curr == 1) {
            return process(2, rest - 1, aim, N);
        } else if (curr == N) {
            return process(N - 1, rest - 1, aim, N);
        } else {
            return process(curr - 1, rest - 1, aim, N) + process(curr + 1, rest - 1, aim, N);
        }
    }

    public static int ways11(int N, int start, int aim, int K) {
        return process2(start, K, aim, N);
    }
    private static int process2(int curr, int rest, int aim, int N) {
        if (rest == 0) {
            return curr == aim ? 1 : 0;
        }
        if (curr == 1) {
            return process2(2, rest - 1, aim, N);
        } else if (curr == N) {
            return process2(N - 1, rest - 1, aim, N);
        } else {
            return process2(curr - 1, rest - 1, aim, N)
                    + process2(curr + 1, rest - 1, aim, N);
        }
    }

    // 每次递归只有2个变量有关curr rest
    // 递归过程中有重复解，可以用记忆化搜索 -》 严格表结构实现动态规划

    //动态规划 - 傻缓存
    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start > N || aim < 1 || aim > N || K < 1) return  -1;

        int[][] dp = new int[N + 1][K + 1]; //缓存表
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1; //表示还没走到
            }
        }
        return process2(start, K, aim, N, dp);
    }

    private static int process2(int curr, int rest, int aim, int N, int[][] dp) {
        if (dp[curr][rest] != -1) {
            return dp[curr][rest];
        }
        int ans = 0;

        if (rest == 0) {
            ans = curr == aim ? 1 : 0;
        } else if (curr == 1) {
            ans = process2(2, rest - 1, aim, N, dp);
        } else if (curr == N) {
            ans = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process2(curr - 1, rest - 1, aim, N, dp) + process2(curr + 1, rest - 1, aim, N, dp);
        }
        dp[curr][rest] = ans;
        return ans;
    }

    //动态规划 - 优化缓存表
    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }

        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;

        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int curr = 2; curr < N; curr++) {
                dp[curr][rest] = dp[curr - 1][rest - 1]  + dp[curr + 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }

    public static int ways33(int N, int start, int aim, int K) {
        int[][] dp = new int[N + 1][K + 1]; //i - curr; j - rest
        dp[aim][0] = 1;

        for (int j = 1; j <= K; j++) {
            dp[1][j] = dp[2][j - 1];
            for (int i = 2; i < N; i++) {
                dp[i][j] = dp[i - 1][j - 1] + dp[i + 1][j - 1];
            }
            dp[N][j] = dp[N - 1][j - 1];
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5,2,4,6));
        System.out.println(ways2(5,2,4,6));
        System.out.println(ways3(5,2,4,6));
    }
}
