package class47_dp;

/*
整型数组arr长度为n(3 <= n <= 10^4)，最初每个数字是<=200的正数且满足如下条件：
1. arr[0] <= arr[1]
2.arr[n-1] <= arr[n-2]
3. arr[i] <= max(arr[i-1], arr[i+1])
但是在arr有些数字丢失了，比如k位置的数字之前是正数，
丢失之后k位置的数字为0。
请你根据上述条件， 计算可能有多少种不同的arr可以满足以上条件。
比如 [6,0,9] 只有还原成 [6,9,9]满足全部三个条件，所以返回1种。

外部信息简化
 */
public class RestoreWays {
    public static int ways1(int[] arr) {
        int N = arr.length;
        if (arr[N - 1] != 0) {
            return process11(arr, N - 1, arr[N - 1], 2);

        } else {
            int ways = 0;
            for (int v = 1; v <= 200; v++) {
                ways += process11(arr, N - 1, v, 2);
            }
            return ways;
        }
    }

    //从右往左
    //i - [0...i]上求答案
    //v - arr[i]变成了v
    //s==0, 代表arr[i] < arr[i + 1]
    //s==1, 代表arr[i] == arr[i + 1]
    //s==0, 代表arr[i] > arr[i + 1]
    private static int process1(int[] arr, int i, int v, int s) {
        if (i == 0) {
            return ((s == 0 || s == 1) && (arr[0] == 0 || arr[0] == v)) ? 1 : 0;
        }

        //i > 0
        if (arr[i] != 0 && arr[i]  != v)
            return 0;

        //i > 0,  并且arr[i]可用变成v
        int ways = 0;
        if (s == 0 || s == 1) {
            for (int pre = 1; pre <= 200; pre++) {
                ways += process1(arr, i - 1, pre, pre < v ? 0 : (pre == v ? 1 : 2));
            }
        } else {
            for (int pre = v; pre <= 200; pre++) {
                ways += process1(arr, i - 1, pre, pre == v ? 1 : 2);
            }
        }
        return ways;
    }

    private static int process11(int[] arr, int i, int v, int s) {
        if (i == 0) {
            return ((s == 0 || s == 1) && (arr[0] == 0 || arr[0] == v)) ? 1 : 0;
        }

        //i > 0
        if (arr[i] != 0 && arr[i]  != v)
            return 0;

        int ways = 0;
        if (s == 0 || s == 1) {
            for (int pre = 1; pre < v; pre++) {
                ways += process11(arr, i - 1, pre, 0);
            }
        }
        ways += process11(arr, i - 1, v, 1);
        for (int pre = v + 1; pre <= 200; pre++) {
            ways += process11(arr, i - 1, pre, 2);
        }
        return ways;
    }

    public static int ways2(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];

        //初始化
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int v = 1; v <= 200; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v <= 200; v++) {
                for (int s = 1; s <= 2; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 0 || s == 1) {
                            for (int pre = 1; pre < v; pre++) {
                                dp[i][v][s] += dp[i - 1][pre][0];
                            }
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        for (int pre = v + 1; pre <= 200; pre++) {
                            dp[i][v][s] += dp[i - 1][pre][2];
                        }
                    }
                }
            }
        }

        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ways = 0;
            for (int v = 1; v <= 200; v++) {
                ways += dp[N - 1][v][2];
            }
            return ways;
        }
    }

    public static int ways3(int[] arr) {
        int N = arr.length;
        int[][][] dp = new int[N][201][3];
        //初始化
        if (arr[0] != 0) {
            dp[0][arr[0]][0] = 1;
            dp[0][arr[0]][1] = 1;
        } else {
            for (int v = 1; v <= 200; v++) {
                dp[0][v][0] = 1;
                dp[0][v][1] = 1;
            }
        }

        //前缀和数组
        int[][] presum = new int[201][3];
        for (int v = 1; v <= 200; v++) {
            for (int s = 1; s <= 2; s++) {
                presum[v][s] = presum[v - 1][s] + dp[0][v][s];
            }
        }

        for (int i = 1; i < N; i++) {
            for (int v = 1; v <= 200; v++) {
                for (int s = 1; s <= 2; s++) {
                    if (arr[i] == 0 || arr[i] == v) {
                        if (s == 0 || s == 1) {
                            dp[i][v][s] += (presum[v -1][s] - presum[0][s]);
                        }
                        dp[i][v][s] += dp[i - 1][v][1];
                        dp[i][v][s] += presum[200][s] - presum[v][s];
                    }
                }
            }
            //去下一个i之前，记录i的presum
            for (int v = 1; v <= 200; v++) {
                for (int s = 1; s <= 2; s++) {
                    presum[v][s] = presum[v - 1][s] + dp[i][v][s];
                }
            }
        }
        if (arr[N - 1] != 0) {
            return dp[N - 1][arr[N - 1]][2];
        } else {
            int ways = 0;
            for (int v = 1; v <= 200; v++) {
                ways += dp[N - 1][v][2];
            }
            return ways;
        }
    }
}
