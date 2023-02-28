package class24_slidewindow;

import java.util.Deque;
import java.util.LinkedList;

// 测试链接：https://leetcode.com/problems/gas-station
public class GasStation {
    //此解法能得到所有能绕完一圈的位置，时间复制度O(N) 空间复杂度O(N)
    //原题只要求找出一个能绕完一圈的位置
    public static int canCompleteCircuit1(int[] gas, int[] cost) {
        boolean[] res = calculate(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (res[i]) {
                return i;
            }
        }
        return -1;
    }

    //gas + cost得到纯能值数组，从i出发如果累加一圈的过程中没有负数，说明是良好出发点
    // 每个位置转一圈，会是O(n*n)的复杂度
    // 优化：纯能值数组 -> 前缀累加和数组help -> help数组扩一倍，继续加
    /* 纯能值: 6 -1 -2 4 -2 -4
       help: 6 5 3 7 5 1
       help扩一倍: 6 5 3 7 5 1 7 6 4 8 6 2
       以i为出发点，窗口（N个元素）内没有出现负数，也即窗口内最小值不是零以下，就说明i是良好出发点
       0 出发
       i (i > 0） 出发 原始累加和要计算一下 = help[j] - i左边的元素
     */
    private static boolean[] calculate(int[] gas, int[] cost) {
        int N = gas.length;
        int M = N << 1; // N * 2
        int[] help = new int[M];
        for (int i = 0; i < N; i++) {
            help[i] = gas[i] - cost[i];
            help[i + N] = gas[i] - cost[i];
        }
        for (int i = 1; i < M; i++) {
            help[i] += help[i - 1];
        }

        Deque<Integer> w = new LinkedList<>();
        //窗口培养
        for (int i = 0; i < N; i++) {
            while (!w.isEmpty() && help[w.peekLast()] >= help[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        boolean[] res = new boolean[N];
        //i是当前要判断的位置，从该位置出发是否能绕一圈
        for (int offset = 0, i = 0, R = N; R < M; offset = help[i], i++, R++) {
            if (help[w.peekFirst()] - offset >= 0) {
                res[i] = true;
            }
            //判断过期，此时i已经判断过了，如果还在窗口内，可以删除
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            //窗口往右边扩
            while (!w.isEmpty() && help[w.peekLast()] >= help[R]) {
                w.pollLast();
            }
            w.addLast(R);
        }
        return res;
    }

    //LeetCode题解, 按题意只返回一个能绕完一圈的位置
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int N = gas.length;
        int i = 0;

        while (i < N) {
            //尝试从某个位置i出发
            int cnt = 0;
            int g = 0, c = 0;
            while (cnt < N)  {
                int j = (i + cnt) % N;
                g += gas[j];
                c += cost[j];
                if (g < c) {
                    break;
                }
                cnt++;
            }

            if (cnt == N) { //说明i能绕完一圈
                return i;
            } else {
                i = i + cnt + 1; //说明从i出发可以经过cnt个加油站，但无法饶完一圈，下次尝试从第一个无法到达的位置出发继续检查
            }
        }

        return -1;
    }



    //优化空间复杂度 时间复制度O(N) 空间复杂度O(1)
    public static int canCompleteCircuit2(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0) {
            return -1;
        }
        if (gas.length == 1) {
            return gas[0] < cost[0] ? -1 : 0;
        }
        boolean[] good = station(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }


    private static boolean[] station(int[] gas, int[] cost) {
        //得到纯能值数组，cost原地转换为纯能值， 并选一个起始数
        int init = changeDisArrayAndGetInit(cost, gas);
        return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
    }

    //扩大连通区
    private static boolean[] enlargeArea(int[] dis, int init) {
        boolean[] res = new boolean[dis.length];
        int start = init;
        int end = nextInd(init, dis.length);
        int rest = 0, need = 0;
        //TODO ..
        return res;
    }

    private static int nextInd(int ind, int len) {
        return ind == len - 1 ? 0 : (ind +  1);
    }

    private static int lastInd(int ind, int len) {
        return ind == 0 ? (len - 1) : (ind - 1);
    }

    private static int changeDisArrayAndGetInit(int[] dis, int[] gas) {
        int init = -1;
        for (int i = 0; i < dis.length; i++) {
            dis[i] = gas[i] - dis[i];
            if (dis[i] >= 0) {
                init = i;
            }
        }
        return init;
    }
}
