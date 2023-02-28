package class22_recursion_dp;

/*
arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
每个值都认为是一种面值，且认为张数是无限的。
返回组成aim的最少货币数
 */
public class Code02_MinCoinsNoLimit {

    public static int minCoins(int[] arr, int aim) {
        return  process(arr, 0, aim);
    }

    //arr[index...]返回最少张数
    //Integer.MAX_VALUE 标记无法完成正好凑到rest这么多钱
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int ans = Integer.MAX_VALUE;
        for (int cnt = 0; cnt * arr[index] <= rest; cnt++) {
            int next = process(arr, index + 1, rest - cnt * arr[index]);
            if (next != Integer.MAX_VALUE) {
                ans = Math.min(ans, next + cnt);
            }
        }
        return ans;
    }

    public static int minCoinsDp(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = Integer.MAX_VALUE;
                for (int cnt = 0; cnt * arr[i] <= rest; cnt++) {
                    int next = dp[i + 1][rest - cnt * arr[i]];
                    if (next != Integer.MAX_VALUE) {
                        dp[i][rest] = Math.min(dp[i][rest], next + cnt);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    //斜率优化
    public static int minCoinsDp2(int[] arr, int aim) {
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = dp[i + 1][rest];
                if (rest >= arr[i] //没有越界，并且是有解的
                        && dp[i][rest - arr[i]] != Integer.MAX_VALUE) {
                    dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                }
            }
        }

        return dp[0][aim];
    }

    public static int[] radomArray(int maxLen, int maxValue) {
        int N = (int)(Math.random() * maxLen);
        int[] arr = new int[N];

        boolean has[] = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int)(Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int maxLen = 20;
        int maxVal = 30;
        int testTime = 300;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int)(Math.random() * maxLen);
            int[] arr = radomArray(N, maxVal);
            int aim = (int)(Math.random() * maxVal);
            int ans1 = minCoins(arr, aim);
            int ans2 = minCoinsDp(arr, aim);
            int ans3 = minCoinsDp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
            }
        }
        System.out.println("测试结束");
//        int[] arr = {1,3,5,2,15,10};
//        System.out.println(minCoins(arr, 18));
//        System.out.println(minCoinsDp(arr, 18));
//        System.out.println(minCoinsDp2(arr, 18));
    }
}
