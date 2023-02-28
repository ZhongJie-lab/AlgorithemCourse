package class40_sumSubArray;

/*
给定一个正整数组成的无序数组arr，给定一个正整数值K
找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
返回其长度
解：都是正整数，范围变化具有单调性，可以用窗口
 */
public class LongestSumSubArrayLengthInPositiveArray {
    public static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }
        int L = 0;
        int R = 0;
        int sum = arr[0];
        int len = 0;

        while (R < arr.length) {
            if (sum == K) {
                len = Math.max(len, R - L + 1);
                sum -= arr[L++];
            } else if (sum < K) {
                R++;
                //要不要往后尝试？如果已经是最后一个数了，停
                if (R == arr.length) {
                    break;
                }
                sum += arr[R];
            } else {
                sum -= arr[L++];
            }
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

    public static int[] generatePositiveArray(int maxLen, int maxVal) {
        int[] arr = new int[maxLen];
        for (int i = 0; i < maxLen; i++) {
            arr[i] = (int)(Math.random() * maxVal) + 1;
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
            int[] arr = generatePositiveArray(len, value);
            int K = (int)(Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
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
