package class24_slidewindow;

import java.util.Deque;
import java.util.LinkedList;

/*
    给定一个整型数组arr，和一个整数num
    某个arr中的子数组sub，如果想达标，必须满足：
    sub中最大值 – sub中最小值 <= sum，
    返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {
    //1，一旦确定子数组[L...R]是达标的，那么其内部所有子数组都达标
    //2. 如果[L...R]是不达标的，那么往左或往右扩也是不达标的
    //一次算一批，计算以L开头的达标的子数组，L [0 ...]
    //定义两个2窗口，minWindow maxWindow
    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        Deque<Integer> minWin = new LinkedList<>();
        Deque<Integer> maxWin = new LinkedList<>();

        int R = 0; //[L, R) 窗口左闭右开，一开始是[0, 0) 窗口内没数
        for (int L = 0; L < N; L++) {
            //窗口培养
            //[L...R 初次不达标，停
            while (R < N) {
                while (!minWin.isEmpty() && arr[minWin.peekLast()] >= arr[R]){
                    minWin.pollLast();
                }
                minWin.addLast(R);

                while(!maxWin.isEmpty() && arr[maxWin.peekLast()] <= arr[R]) {
                    maxWin.pollLast();
                }
                maxWin.addLast(R);

                if (arr[maxWin.peekFirst()] - arr[minWin.peekFirst()] > sum) {
                    break;
                } else {
                    R++;
                }
            }
            count += R - L; //以arr[L]开头的[L, R)范围上达标的子数组个数
            //删除队头过期的元素 L马上要++，如果窗口第一个元素是L就是是过期元素，删除
            if (minWin.peekFirst() == L) {
                minWin.pollFirst();
            }
            if (maxWin.peekFirst() == L) {
                maxWin.pollFirst();
            }
        }
        return count;
    }

    //暴力方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;

        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    //for test
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int)(Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue + 1) - (int) (Math.random() * maxValue + 1);
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
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 3000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int sum = (int)(Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
