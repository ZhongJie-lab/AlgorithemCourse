package class20_recursion_dp;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Coffee {

    /*
        arr - 一组咖啡机，arr[i]第i号咖啡机冲咖啡的时间 （假设每个人喝完一杯咖啡的时间是0）
        n - 有n个人要和咖啡
        a - 一台咖啡机洗一个咖啡杯的时间
        b - 一个咖啡杯挥发干净的时间

        返回所有咖啡杯变干净的最少时间
     */

    //优良暴力 + 贪心
    public static int minTime(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>((o1, o2) -> (o1.timePoint + o1.workTime) - (o2.timePoint - o2.workTime));

        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine curr = heap.poll();
            curr.timePoint += curr.workTime;
            drinks[i] = curr.timePoint; //什么时候喝完咖啡
            heap.add(curr);
        }

        //什么时候咖啡杯变干净
        return bestTime(drinks, a, b, 0, 0);
    }

    //drinks - 喝完咖啡的时间点
    //wash - 洗干净一个杯子的时间
    //air - 挥发干净一个杯子的时间
    //index -  drinks[index....]都变干净，返回最早的时间
    //free 洗咖啡杯的机器什么时候可用
    private static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        if (index == drinks.length) {
            return 0;
        }

        //index号杯子决定洗干净
        int cleanTime1 = Math.max(drinks[index], free) + wash; //index号杯子洗干净的时间
        int restCleanTime1 = bestTime(drinks, wash, air, index + 1, cleanTime1); //其他杯子变干净的时间
        int p1 = Math.max(cleanTime1, restCleanTime1);

        //index号杯子决定挥发干净
        int cleanTime2 = drinks[index] + air; //index号杯子挥发干净的时间
        int restCleanTime2 = bestTime(drinks, wash, air, index + 1, free); //其他杯子变干净的时间
        int p2 = Math.max(cleanTime2, restCleanTime2);

        return Math.min(p1, p2);
    }

    //优良尝试改成动态规划 + 贪心
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<>((o1, o2) -> (o1.timePoint + o1.workTime) - (o2.timePoint - o2.workTime));
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }

        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine curr = heap.poll();
            curr.timePoint += curr.workTime;
            drinks[i] = curr.timePoint;
            heap.add(curr);
        }
        return bestTimeDP(drinks, a, b);
    }

    private static int bestTimeDP(int[] drinks, int wash, int air) {
        int N = drinks.length;
        int maxFree = 0; //每个杯子都用机器洗，串行
        for (int i = 0; i < N; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1]; //默认dp[N][i] = 0;
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                //index号杯子决定洗干净
                int cleanTime1 = Math.max(drinks[index], free) + wash; //index号杯子洗干净的时间
                if (cleanTime1 > maxFree) { //时间有可能大于最大时间，那后面就不用填了
                    break;
                }
                int restCleanTime1 = dp[index + 1][cleanTime1]; //其他杯子变干净的时间
                int p1 = Math.max(cleanTime1, restCleanTime1);

                int cleanTime2 = drinks[index] + air; //index号杯子挥发干净的时间
                int restCleanTime2 = dp[index + 1][free]; //其他杯子变干净的时间
                int p2 = Math.max(cleanTime2, restCleanTime2);

                dp[index][free] = Math.min(p1, p2);
            }
        }

        return dp[0][0];
    }


    public static class Machine {
        public int timePoint; //当前时间点
        public int workTime; //冲咖啡的时间

        public Machine(int tp, int wt) {
            this.timePoint = tp;
            this.workTime = wt;
        }
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始！");

        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int)(Math.random() * 7) + 1;
            int a = (int)(Math.random() * 7) + 1;
            int b = (int)(Math.random() * 10) + 1;
            int ans1 = minTime(arr, n, a, b);
            int ans2 = minTime2(arr, n, a, b);
            if (ans1 != ans2) {
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + ", " + ans2);
                System.out.println("=================");
                break;
            }

        }
        System.out.println("测试结束！");
    }

    private static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int)(Math.random() * max) + 1;
        }
        return arr;
    }
}
