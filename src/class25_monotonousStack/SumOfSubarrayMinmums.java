package class25_monotonousStack;

//https://leetcode.cn/problems/sum-of-subarray-minimums/
/*
给定一个数组arr，
返回所有子数组最小值的累加和
 */
public class SumOfSubarrayMinmums {
    //暴力解
    public static int subArrayMinSum(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = arr[i];
                for (int k = i + 1; k <= j; k++) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        return ans;
    }

    //以每个数为最小值的子数组，计算最小值，再累加
    //没用单调栈
    public static int subArrayMinSum2(int[] arr) {
        int[] left = leftNearLessEqual(arr); //left[i] = x，arr[i]左边，离arr[i]最近的 <= arr[i]，位置在x
        int[] right = rightNearLess(arr); //right[i] = y，arr[i]右边，离arr[i]最近的 < arr[i]，位置在y
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int start = i - left[i];
            int end = right[i] - i;
            ans += start * end * arr[i];
        }
        return ans;
    }

    private static int[] leftNearLessEqual(int[] arr) {
        int N = arr.length;
        int[] left = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    private static int[] rightNearLess(int[] arr) {
        int N = arr.length;
        int[] right = new int[N];
        for (int i = 0; i < N; i++) {
            int ans = -1;
            for (int j = i + 1; j < N; j++) {
                if (arr[i] > arr[j]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }

        return right;
    }

    //单调栈
    public static int subArrayMinSum3(int[] arr) {
        int[] stack = new int[arr.length];
        int[] left = leftNearLessEqual(arr, stack); //left[i] = x，arr[i]左边，离arr[i]最近的 <= arr[i]，位置在x
        int[] right = rightNearLess(arr, stack); //right[i] = y，arr[i]右边，离arr[i]最近的 < arr[i]，位置在y
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long)arr[i];
            ans %= 1000000007;
        }
        return (int)ans;
    }

    private static int[] leftNearLessEqual(int[] arr, int[] stack) {
        int N = arr.length;
        int[] left = new int[N];
        int size = 0;
        for (int i = N - 1; i >= 0; i--) { //从后往前算
            while (size != 0 && arr[stack[size - 1]] >= arr[i]) {
                int j = stack[size - 1];
                left[stack[--size]] = i;  //弹出并得到左边界i
            }
            stack[size++] = i; //push
        }
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }

    private static int[] rightNearLess(int[] arr, int[] stack) {
        int N = arr.length;
        int[] right = new int[N];
        int size = 0;
        for (int i = 0; i < N; i++) {
            while (size != 0 && arr[stack[size - 1]] > arr[i]) {
                right[stack[--size]] = i; //弹出并得到右边界i
            }
            stack[size++] = i; //push
        }

        while (size != 0) {
            right[stack[--size]] = N;
        }
        return right;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subArrayMinSum(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = subArrayMinSum3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
