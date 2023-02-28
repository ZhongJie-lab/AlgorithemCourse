package class21_recursion_dp;

/*
arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
每个值都认为是一种面值，且认为张数是无限的。
返回组成aim的方法数
例如：arr = {1,2}，aim = 4
方法如下：1+1+1+1、1+1+2、2+2
一共就3种方法，所以返回3
 */
public class Code02_CoinsWayNoLimit {
    public static int coinsWays(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    //arr[index....]有几种方式正好组成rest, 每个面值都可以任意选择张数
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) { //没钱了
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int cnt = 0; arr[index] * cnt <= rest ; cnt++) { //本轮选arr[index] 0,1,2,....张
            ways += process(arr, index + 1, rest - (cnt * arr[index]));
        }
        return ways;
    }

    //动态规划 - 严格表结构
    public static int coinsWays2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int cnt = 0; arr[i] * cnt <= rest ; cnt++) { //此次枚举行为，可以通过优化省略
                    ways += dp[i + 1][rest - (cnt * arr[i])];
                }
                dp[i][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    //动态规划 - 严格表结构，进一步优化，避免枚举
    public static int coinsWays3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = dp[i + 1][rest];
                if (rest - arr[i] >= 0) {
                    ways += dp[i][rest - arr[i]];  //dp[i][rest - arr[i]]，就是枚举中，从rest - (1* arr[i]) 减去一张面值开始
                }

                dp[i][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        System.out.println(coinsWays(arr, 8));
        System.out.println(coinsWays2(arr, 8));
        System.out.println(coinsWays3(arr, 8));
    }
}
