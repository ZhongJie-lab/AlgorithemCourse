package class24_slidewindow;

import java.util.Deque;
import java.util.LinkedList;

/*
假设一个固定大小为W的窗口，依次划过arr，
返回每一次滑出状况的最大值
例如，arr = [4,3,5,4,3,3,6,7], W = 3
返回：[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1) {
            return null;
        }
        int N = arr.length - w + 1;
        int[] res = new int[N];
        Deque<Integer> queue = new LinkedList<>(); //存放的是下标
        int index = 0;
        for (int R = 0; R < arr.length; R++) {
            //维持头大尾小，出队列，从队尾出
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[R]) {
                queue.pollLast();
            }
            //入队列，从队尾入
            queue.addLast(R);

            //判断队头有没有过期，从队头出队列，维持当前观察的窗口，删除过期元素
            if (queue.peekFirst() == R - w) {
                queue.pollFirst();
            }
            //如果窗口形成，队头就是当前窗口最大值，收集答案
            if (R >= w - 1) {
                res[index++] = arr[queue.peekFirst()];
            }
        }

        return res;
    }

    public static int[] getMaxWindow2(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w > arr.length) {
            return null;
        }
        int[] ans = new int[arr.length - w + 1];
        Deque<Integer> window = new LinkedList<>();
        int ind = 0;
        for (int R = 0; R < arr.length; R++) {
            //维持窗口内位置头大尾小
            while (!window.isEmpty() && arr[R] >= arr[window.peekLast()]) {
                window.pollLast();
            }
            window.addLast(R);

            //查看队头元素是否过期
            if (window.peekFirst() == R - w) {
                window.removeFirst();
            }

            //判断窗口形成，收集答案
            if (R >= w - 1) {
                ans[ind++] = arr[window.peekFirst()];
            }
        }
        return ans;
    }

    public static int[] getMaxWindow3(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1) {
            return null;
        }
        int N = arr.length - w + 1;
        int[] ans = new int[N];
        int ind = 0;
        Deque<Integer> queue = new LinkedList<>();

        for (int R = 0; R < arr.length; R++) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[R]) {
                queue.pollLast();
            }

            queue.addLast(R);

            if (queue.peekFirst() == R - w) {
                queue.pollFirst();
            }

            if (R >= w - 1) {
                ans[ind++] = arr[queue.peekFirst()];
            }
        }

        return ans;
    }

    public static int[] getMaxWwindow4(int[] arr, int w) {
        if (arr == null || arr.length == 0 || w < 1) {
            return null;
        }
        int N = arr.length - w + 1;
        int[] ans = new int[N];
        int ind = 0;
        Deque<Integer> queue = new LinkedList<>();
        for (int R = 0; R < arr.length; R++) {
            while (!queue.isEmpty() && arr[queue.peekLast()] <= arr[R]) {
                queue.pollLast();
            }
            queue.addLast(R);

            if (queue.peekFirst() == R - w) {
                queue.pollFirst();
            }

            if (R >= w - 1) {
                ans[ind++] = arr[queue.peekFirst()];
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] nums = {4,3,5,4,3,3,6,7};
        int w = 3;
        int[] res = getMaxWindow(nums, w);
        for (int n : res) {
            System.out.print(n + " ");
        }
    }
}
