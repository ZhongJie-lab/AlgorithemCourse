package class21_recursion_dp;

import java.util.HashMap;
import java.util.Map;

/*
arr是货币数组，其中的值都是正数。再给定一个正数aim。
每个值都认为是一张货币，
认为值相同的货币没有任何不同，
返回组成aim的方法数
例如：arr = {1,2,1,1,2,1,2}，aim = 4
方法：1+1+1+1、1+1+2、2+2
一共就3种方法，所以返回3
 */
public class Code03_CoinsWaySameValueSamePapper {
    public static int coinsWays(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        Dict dict = getInfo(arr);
        return process(dict.coins, dict.cnt, 0, aim);
    }

    private static int process(int[] coins, int[] cnts, int index, int rest) {
        if (index == coins.length) { //所有coins都尝试了
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int cnt = 0; cnt * coins[index] <= rest && cnt <= cnts[index]; cnt++) {
            ways += process(coins, cnts, index + 1, rest - cnt * coins[index]);
        }
        return ways;
    }

    //动态规划 - 严格表结构
    public static int coinsWays2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        Dict dict = getInfo(arr);
        int[] coins = dict.coins;
        int[] cnts = dict.cnt;
        int N = coins.length;

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int cnt = 0; cnt * coins[index] <= rest && cnt <= cnts[index]; cnt++) {
                    ways += dp[index + 1][rest - cnt * coins[index]];
                }
                dp[index][rest] = ways;
            }
        }

        return dp[0][aim];
    }

    //动态规划 - 严格表结构，观察得到，可优化省略枚举行为
    public static int coinsWay3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        Dict dict = getInfo(arr);
        int[] coins = dict.coins;
        int[] cnts = dict.cnt;

        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];

                if (rest >= coins[index]) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                //受到张数限制，极大张数加1的钱的位置要剪掉，它是对dp[index][rest - coins[index]] 有效的值，但是对dp[index][rest] 无效，因为超出张数限制了
                //有可能coins[index]很大，比如100万张，在这个逻辑之前就越界了，这种情况就不用剪了
                if (rest - (cnts[index] + 1) * coins[index] >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - (cnts[index] + 1) * coins[index]];
                }
            }
        }
        return dp[0][aim];
    }


    private static Dict getInfo(int[] arr) {
        Map<Integer, Integer> map  = new HashMap<>();
        for (int val : arr) {
            map.put(val, map.getOrDefault(val, 0)+1);
        }

        int N = map.size();
        int[] coins = new int[N];
        int[] cnt = new int[N];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            coins[i] = entry.getKey();
            cnt[i] = entry.getValue();
            i++;
        }
        return new Dict(coins, cnt);
    }

    //统计面值和张数
    public static class Dict{
        int[] coins;
        int[] cnt;

        public Dict(int[] coins, int[] cnt) {
            this.coins = coins;
            this.cnt = cnt;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 1, 1, 2, 1, 2};
        System.out.println(coinsWays(nums, 4));
        System.out.println(coinsWays2(nums, 4));
        System.out.println(coinsWay3(nums, 4));
    }
}
