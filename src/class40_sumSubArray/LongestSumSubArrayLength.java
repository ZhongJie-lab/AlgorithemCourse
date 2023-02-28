package class40_sumSubArray;

import java.util.HashMap;

/*
给定一个整数组成的无序数组arr，值可能正、可能负、可能0
给定一个整数值K
找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
返回其长度
7 2 3 5 -5 3 2
解：遍历求以i结尾的子数组的累加和为K的最大的长度，[0..j..i]的前缀和是sum，等同于求最早出现前缀和等于sum-k的[0..j]，[i..j]的和是k，收集答案
准备一张表map：K - 前缀和sum；V -最早出现sum的位置
 */
public class LongestSumSubArrayLength {
    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        //K - 前缀和sum
        //V -最早出现sum的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); //表示一个数也没有的时候，前缀和是0；如果没有这条记录，会错过所有从0开头的答案

        int sum = 0;
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)) { //收集答案
                len = Math.max(len, i - map.get(sum - k));
            }

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
            //如果sum已经存在，也不要更新map，因为要知道最早出现前缀和的位置，现在加进去的话不是最早出现sum的那个位置
        }
        return len;
    }

    // for test
    public static int right(int[] arr, int K) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    // for test
    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    //for test
    public static int[] generateRandomArray(int maxLen, int maxVal) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int)(Math.random() * maxVal) - (int)(Math.random() * maxVal);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int times = 10000;

        System.out.println("test begin");
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int)(Math.random() * value) - (int)(Math.random() * value);
            int ans1 = maxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops");
                printArray(arr);
                System.out.println("K" + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");
    }
}
