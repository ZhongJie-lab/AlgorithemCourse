package class5_6_10_sort.mergesort;

// https://leetcode-cn.com/problems/count-of-range-sum/
//【Hard】给定一个数组arr，两个整数lowder和upper，返回arr中有多少个子数组的累加和在[lowder, upper]范围上
public class CountOfRangeSum {
    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) return 0;

        //前缀和数组
        long[] sum = new long[nums.length]; //long类型防止溢出
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] += sum[i-1] + nums[i];
        }
        return process(sum, 0, nums.length - 1, lower, upper);
    }

    //假设0 - i的整体累加和是x, 求必须以i位置结尾的子数组，有多少个区间在[lower, upper]上
    //等同于，求i之前的所有前缀和中，有多少个前缀和落在[x - upper, x - lower]上
    private static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) { //[0 .. i] 子数组本身是不是在范围内，一个数也不减掉
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int mid = L + (R - L) / 2;

        return process(sum, L, mid, lower, upper) +
                process(sum, mid + 1, R, lower, upper) +
                merge(sum, L, mid, R, lower, upper);
    }

    //左组和右组 - 左组元素和右组元素在原数组中的相对顺序是，左组所有的都在右组前面
    //从前往后，遍历右组中的每一个数，找出左组中有多少个数落在[x - upper, x - lower]上
    //因为有序，每个数的范围上限一直在递增，下限也在递增，所以整个遍历是不回退的
    //在左边的组，设置窗口[windowL, windowR)，左右指针分别滑动， 窗口内的数是符合要求的
    //然后，正常merge
    private static int merge(long[] sum, int l, int mid, int r, int lower, int upper) {
        int res = 0;
        int windowL = l, windowR = l; //初始，窗口内一个数也没有，而且不回退
        for (int i = mid + 1; i <= r; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (windowR <= mid && sum[windowR] <= max) {
                windowR++;
            } //右开

            while (windowL <= mid && sum[windowL] < min) {
                windowL++;
            } //左闭
            res += windowR - windowL;
        }

        //正常merge
        int p = 0, p1 = l, p2 = mid + 1;
        long[] tmp = new long[r - l + 1];
        while(p1 <= mid && p2 <= r) {
            tmp[p++] = sum[p1] < sum[p2] ? sum[p1++] : sum[p2++];
        }
        while (p1 <= mid) {
            tmp[p++] = sum[p1++];
        }
        while (p2 <= r) {
            tmp[p++] = sum[p2++];
        }

        for (int i = 0; i < r - l + 1; i++) {
            sum[i + l] = tmp[i];
        }
        return res;
    }


}
