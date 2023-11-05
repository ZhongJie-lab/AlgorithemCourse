package class2_binarysearch;

public class Code03_BSAwesome {
    /**
     * 无序数组，任意2个相邻的数不相等
     * 找出局部最小
     * 局部最小定义：
     *  1. 0位置是局部最小，要求arr[0] < arr[1]
     *  2. N - 1 位置是局部最小，要求arr[N-2] > arr[N-1]
     *  3. i位置是局部最小，要求arr[i-1] > arr[i] < arr[i+1]
     */
    public static int findFootElement(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        int N = arr.length;
        if (N == 1 || arr[0] < arr[1]) return 0;
        if (arr[N - 2] > arr[N - 1]) return N - 1;

        //趋势：arr[0] < arr[1]且arr[N - 2] > arr[N - 1]，开头下降，最后上扬，相邻数不相等，则1 ~ N-1 之间必出现局部最小
        int L = 1, R = N - 2;

        while (L < R) {
            int mid = L + (R - L)/2;
            if (arr[mid] > arr[mid - 1]) { //左侧出现趋势
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) { //右侧出现趋势
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return L;
    }

    public static int findFootElement2(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        int N = arr.length;
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return 1;
        }

        int L = 0, R = N - 1;
        while (L < R) {
            int mid = L + (R - L) / 2;
            if (arr[mid] < arr[L]) {

            }
        }
        return -1;
    }

    // 峰值元素是指其值严格大于左右相邻值的元素
    // 给你一个整数数组 nums，已知任何两个相邻的值都不相等
    // 找到峰值元素并返回其索引
    // 数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
    // 你可以假设 nums[-1] = nums[n] = 无穷小
    // 0位置是峰值，则 nums[0] > nums[1]
    // N -1位置是峰值，则 nums[N - 1] > nums[N - 2]
    // i 位置是峰值，则 nums[i] > nums[i - 1] 且 nums[i] < nums[i + 1]
    // 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
    // https://leetcode.cn/problems/find-peak-element/
    public static int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        if (nums.length < 2) return 0;

        int N = nums.length;
        if (nums[0] > nums[1]) return 0;
        if (nums[N - 1] > nums[N - 2]) return N - 1;

        int ans = -1;
        int l = 1, r = N - 2;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (nums[mid - 1] > nums[mid]) {
                r = mid - 1;
            } else if (nums[mid] < nums[mid + 1]){
                l = mid + 1;
            } else {
                ans = mid;
                break;
            }
        }
        return ans;
    }
}
