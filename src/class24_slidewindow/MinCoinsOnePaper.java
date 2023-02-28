package class24_slidewindow;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
arr是货币数组，其中的值都是正数。再给定一个正数aim。
每个值都认为是一张货币，
返回组成aim的最少货币数
注意：因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
注意：Code03_CoinsWaySameValueSamePapper 求方法数，这里是求最少货币
 */
public class MinCoinsOnePaper {
    //递归 - 朴素尝试
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int p1 = process(arr, index + 1, rest); //不用当前的coin
        int p2 = process(arr, index + 1, rest - arr[index]); //用当前的coin
        if (p2 != Integer.MAX_VALUE) {
            p2++;
        }
        return Math.min(p1, p2);
    }

    //动态规划 - 朴素尝试 O(N * aim)
    public static int minCoinsDp(int[] arr, int aim) {
        if (aim == 0) return 0;

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
                if (p2 != Integer.MAX_VALUE) {
                    p2++;
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    //动态规划 - 词频统计 朴素尝试 O(M * aim * 每种货币的平均张数) M是arr压缩后的，钞票面值种数
    public static int minCoinsDp2(int[] arr, int aim) {
        if (aim == 0) return 0;

        Dict dict = getDict(arr);
        int[] coins = dict.coins;
        int[] cnts = dict.cnts;
        int N = coins.length;

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                //index号钞票最多有cnts[index]张
                for (int cnt = 0; cnt <= cnts[index] && cnt * coins[index] <= aim; cnt++) {
                    if (rest - cnt * coins[index] >= 0
                            && dp[index + 1][rest - cnt * coins[index]] != Integer.MAX_VALUE) { //放溢出 cnt + max
                        dp[index][rest] = Math.min(dp[index][rest], cnt + dp[index + 1][rest - cnt * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    //动态规划 - 词频统计 - 斜率优化，规避枚举，需要用到窗口内的最小值的更新结构
    public static int minCoinsDp3(int[] arr, int aim) {
        if (aim == 0) return 0;

        Dict dict = getDict(arr);
        int[] coins = dict.coins;
        int[] cnts = dict.cnts;
        int N = coins.length;

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }

        //mode 是用来分组的， 例：3元分三组，4元分四组，但不能超过aim组，按组来填充dp表
        for (int i = N - 1; i >= 0; i--) {
            for (int mod = 0; mod < Math.min(coins[i], aim + 1); mod++) {
                // 当前面值x
                // mod   mod + x    mod + 2*x    mod + 3*x
                Deque<Integer> w = new LinkedList<>(); //定义窗口，窗口内放的是rest元，窗口要遵循放进来的元最小组成张数从小到大排列
                w.add(mod);
                dp[i][mod] = dp[i + 1][mod]; //mod元的时候客观来说要用0张，所以结果就是dp[i + 1][mod]
                //窗口往右扩，往左缩，找到最小的
                for (int r = mod + coins[i]; r <= aim; r += coins[i]) {
                    //从队尾删除不符合从小到大的元素
                    while (!w.isEmpty() && (dp[i + 1][w.peekLast()] == Integer.MAX_VALUE
                            || dp[i + 1][w.peekLast()] + compensate(w.peekLast(), r, coins[i]) >= dp[i + 1][r] )) {
                        w.pollLast();
                    }
                    //入队列
                    w.addLast(r);
                    //从队头删除"过期"的，过期的含义 - 不删除的话，计算会超出张数限制
                    int overdue = r - (cnts[i] + 1) * coins[i];
                    if (w.peekFirst() == overdue) {
                        w.pollFirst();
                    }

                    //收集答案 dp[i][r]，从队头拿到最小值
                    dp[i][r] = dp[i + 1][w.peekFirst()] + compensate(w.peekFirst(), r, coins[i]);
                }
            }
        }

        return dp[0][aim];
    }

    //补偿钞票的张数
    private static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    private static Dict getDict(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        int N = map.size();
        int[] coins = new int[N];
        int[] cnts = new int[N];
        int ind = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            coins[ind] = entry.getKey();
            cnts[ind++] = entry.getValue();
        }
        return new Dict(coins, cnts);
    }


    public static class Dict{
        int[] coins;
        int[] cnts;

        public Dict(int[] coins, int[] cnts) {
            this.coins = coins;
            this.cnts = cnts;
        }
    }

    //for test
    public static int[] randomArray(int N, int maxValue) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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
        int maxValue = 30;
        int testTime = 3000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = minCoinsDp(arr, aim);
            int ans3 = minCoinsDp2(arr, aim);
            int ans4 = minCoinsDp3(arr, aim);
            if (ans1 != ans2 || ans3 != ans1 || ans4 != ans3
            ) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
