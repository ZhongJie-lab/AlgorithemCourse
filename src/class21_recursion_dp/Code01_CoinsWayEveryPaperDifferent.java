package class21_recursion_dp;

/*
arr是货币数组，其中的值都是正数。再给定一个正数aim。
每个值都认为是一张货币，
即便是值相同的货币也认为每一张都是不同的，
返回组成aim的方法数
例如：arr = {1,1,1}，aim = 2
第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
一共就3种方法，所以返回3
 */
public class Code01_CoinsWayEveryPaperDifferent {
    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    //arr[index....] 正好组成rest这么多前，有几种方法
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) { //因为后面有扣减逻辑rest - arr[index]，这里要判断是否负数
            return 0;
        }
        if (index == arr.length) { //没钱了！
            return rest == 0 ? 1 : 0;
        }

        return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
    }

    public static int coinWays2(int[] arr, int aim) {
        if (aim == 0) return 1;

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];

        dp[N][0] = 1; //dp[N][j] j != 0 则是 0

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = dp[i + 1][rest];
                if (rest - arr[i] >= 0) {
                    ways += dp[i + 1][rest - arr[i]];
                }
                dp[i][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int[] arr = {1,2,2,3};
        int aim = 4;
        System.out.println(coinWays(arr, aim));
        System.out.println(coinWays2(arr, aim));
    }
}
