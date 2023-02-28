package class29_topK;

import java.util.Arrays;
import java.util.PriorityQueue;

//在无序数组中求第K小的数
public class FindMinKth {
    //方法一 利用大根堆 空间复杂度O(k) ,时间复杂度O(N)
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }

        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }

        return maxHeap.peek();
    }

    //方法二 改写快排 空间复杂度O(1) ,时间复杂度O(N)
    public static int minKth2(int[] arr, int k) {
        int[] nums = copyArray(arr);
        return process(nums, 0, arr.length - 1, k - 1);
    }

    private static int process(int[] nums, int L, int R, int ind) {
        if (L == R) return nums[L];

        //随机选一个数 不止一个数  L +  [0, R -L]
        int pivot = nums[L + (int)(Math.random() * (R - L + 1))];

        int[] range = partition(nums, L, R, pivot);
        if (ind >= range[0] && ind <= range[1]) {
            return nums[ind];
        } else if (ind < range[0]) {
            return process(nums, L, range[0] - 1, ind);
        } else {
            return process(nums, range[1] + 1, R, ind);
        }
    }

    private static int[] partition(int[] nums, int L, int R, int pivot) {
        int lessInd = L - 1;
        int moreInd = R + 1;
        int ind = L;

        while (ind < moreInd) {
            if (nums[ind] < pivot) {
                swap(nums, ind, ++lessInd);
                ind++;
            } else if (nums[ind] == pivot) {
                ind++;
            } else {
                swap(nums, ind, --moreInd);
            }
        }
        return new int[] {lessInd + 1, moreInd - 1};
    }

    public static int minKth22(int[] arr, int k) {
        int[] nums = copyArray(arr);
        int L = 0, R = arr.length - 1;
        int pivot = 0;
        int[] range = new int[2]; //按划分值分区，得到的区间

        while (L < R) {
            //选一个划分值
            pivot = arr[L + (int)(Math.random() * (R - L + 1))];
            range = partition22(nums, L, R, pivot);

            if (k - 1 < range[0]) {
                R = range[0] - 1;
            } else if (k - 1 > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    private static int[] partition22(int[] nums, int L, int R, int pivot) {
        int lessInd = L - 1;
        int moreInd = R + 1;
        int i = L;
        while (i < moreInd) {
            if (nums[i] < pivot) {
                swap(nums, ++lessInd, i++);
            } else if (nums[i] == pivot) {
                i++;
            } else {
                swap(nums, --moreInd, i);
            }
        }
        return new int[] {lessInd+1, moreInd - 1};
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    //方法三 bfprt 空间复杂度O(1) ,时间复杂度O(N) 可以保证每次partition至少有3/10的数被排除，而不是靠概率
    public static int minKth3(int[] arr, int k) {
        int[] nums = copyArray(arr);
        return bfprt(nums, 0, nums.length - 1, k - 1);
    }

    private static int bfprt(int[] nums, int L, int R, int ind) {
        if (L == R) return nums[L];

        int pivot = medianOfMedians(nums, L, R);
        int[] range = partition(nums, L, R, pivot);
        if (ind >= range[0] && ind <= range[1]) {
            return nums[ind];
        } else if (ind < range[0]) {
            return bfprt(nums, L, range[0] - 1, ind);
        } else {
            return bfprt(nums, range[1] + 1, R, ind);
        }
    }

    //nums[L...R] 5个一组，小组内部排序
    //每个小组取中位数，不够5个的取上中位数，组成marr
    //返回 marr的中位数
    private static int medianOfMedians(int[] nums, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];

        for (int t = 0; t < mArr.length; t++) {
            int i = L + t * 5;

            mArr[t] = getMedian(nums, i, Math.min(i + 4, R));
        }
        //找到mArr的中位数
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
//        return getMedian(mArr, 0, mArr.length - 1); 因为mArr数组可能很长，所以，取中位数也需要调用bfprt
    }

    private static int getMedian(int[] nums, int L, int R) {
        sort(nums, L, R);
        return nums[(L + R) / 2];
    }

    private static void sort(int[] nums, int L, int R) {
        for (int i = L + 1; i <= R; i++) { //i是右边界
            for (int j = i - 1; j >= L && nums[j + 1] < nums[j]; j--) {
                swap(nums, j, j + 1);
            }
        }
    }


    //for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    private static void printArray(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                Arrays.sort(arr);
                printArray(arr);

                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("test finish");
    }
}
