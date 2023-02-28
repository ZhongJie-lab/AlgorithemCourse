package class5_6_10_sort.quicksort;

import java.util.Stack;

public class PartitionAndQuickSort {
    //arr[L...R]上，以arr[R]位置的数x做划分，左：<= x 右：> x，最后，arr[R]和 <=区间的后一个位置交换
    //返回 x被交换后的位置
    public static int partition1(int[] arr, int L, int R) {
        if (L > R) return -1;
        if (L == R) return L;
        int ind = L;
        int lessEqualInd = L - 1;
        while (ind < R) {
            if (arr[ind] <= arr[R]) {
                swap(arr, ind, ++lessEqualInd);
            }
            ind++;
        }
        swap(arr, ++lessEqualInd, R);
        return lessEqualInd;
    }

    //arr[L...R], 以arr[R]位置的数x做划分，左：< x，中：= x，右：> x，最后，arr[R] 和 >区间的第一个位置交换
    //返回 2个元素的数组，{x的最左位置，x的最右位置}
    public static int[] partition2(int[] arr, int L, int R) {
        if (L > R) return new int[] {-1, -1};
        if (L == R) return new int[] {L, R};

        int lessInd = L-1;
        int moreInd = R;
        int ind = L;

//        while (ind < moreInd) {
//            if (arr[ind] < arr[R]) {
//                swap(arr, ind, ++lessInd);
//                ind++;
//            } else if (arr[ind] == arr[R]) {
//                ind++;
//            } else {
//                swap(arr, ind, --moreInd);
//            }
//        }
        while (ind < moreInd) {
            if (arr[ind] < arr[R]) {
                swap(arr, ind, ++lessInd);
                ind++;
            } else if (arr[ind] == arr[R]) {
                ind++;
            } else {
                swap(arr, ind, --moreInd);
            }
        }
        swap(arr, moreInd, R);
        //循环结束以后，lessInd是小于区的最后一个数的指针，moreInd是大于区第一个数的前一个指针
        return new int[] {lessInd + 1, moreInd};
    }

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) return;
        process1(arr, 0, arr.length - 1);
    }

    private static void process1(int[] arr, int L, int R) {
        if (L >= R) return; //有可能出现L > R，  比如拿 1 划分， 1，2，5，8，3，此时左边就一个数， process1(arr, 0, -1)
        int M = partition1(arr, L, R);
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    public static void quickSort11(int[] arr) {
        if (arr == null || arr.length == 0) return;

        process11(arr, 0, arr.length - 1);
    }

    private static void process11(int[] arr, int L, int R) {
        if (L >= R) return;
        int pos = partition1(arr, L, R);

        process11(arr, L, pos - 1);
        process11(arr, pos + 1, R);
    }
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) return;
        process2(arr, 0, arr.length - 1);
    }

    private static void process2(int[] arr, int L, int R) {
        if (L >= R) return; //有可能出现 L > R, 如果左边没有 或 右边没有
        int[] equalArea = partition2(arr, L, R);
        process2(arr, L, equalArea[0] - 1);
        process2(arr, equalArea[1] + 1, R);
    }

    //随机选择一个数作为划分，避免最坏的O(n^2)时间复杂度
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) return;
        process3(arr, 0, arr.length - 1);
    }

    private static void process3(int[] arr, int L, int R) {
        if (L >= R) return;
        swap(arr, L + (int)(Math.random() * (R - L + 1)), R); //随机选一个数，避免出现O(n^2)的时间复杂度
        int[] equalsAear = partition2(arr, L, R);
        process3(arr,  L, equalsAear[0] - 1);
        process3(arr, equalsAear[1] + 1, R);
    }

    public static void quickSortNonRecursive(int[] arr) {
        if (arr == null || arr.length < 2) return;
        Stack<Op> stack = new Stack<>();
        int N = arr.length;
        swap(arr, (int)(Math.random() * (N)), N - 1);
        int[] equalArea = partition2(arr, 0, N - 1);
        int el = equalArea[0];
        int er = equalArea[1];

        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, N - 1));
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.el < op.er) {
                swap(arr, op.el + (int)(Math.random()*(op.er - op.el + 1)), op.er);
                equalArea = partition2(arr, op.el, op.er);

                stack.push(new Op(op.el, equalArea[0] - 1));
                stack.push(new Op(equalArea[1] + 1, op.er));
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static class Op{
        int el;
        int er;

        public Op(int el, int er) {
            this.el = el;
            this.er = er;
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");

    }
}

