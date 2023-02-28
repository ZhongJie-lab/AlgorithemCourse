package class29_topK;

/*
给定一个无序数组arr中，长度为N，给定一个正数k，返回top k个最大的数
不同时间复杂度三个方法：

1）O(N*logN)
2）O(N + K*logN)
3）O(n + k*logk)
 */

import java.util.Arrays;

public class MaxTopK {
    // 排序+收集 O(N*logN)
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        Arrays.sort(arr);

        int[] ans = new int[k];
        for (int i = N - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    // 堆排序 O(N + K*logN)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int N = arr.length;
        k = Math.min(k, N);
        //构建大根堆 O(N)
        for (int i = N / 2 - 1; i >= 0; i--) {
            heapify(arr, i, N);
        }

        //只把前k个数，放到arr尾部，然后收集，每次heapify是O(logN)的，k个数要放到末尾做调整，那就是O(k*logN)
        int heapSize = N;
        int cnt = 1;
        swap(arr, 0, --heapSize);
        while (cnt < k & heapSize > 0) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            cnt++;
        }
        int[] ans = new int[k];
        for (int i = N - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    //向下调整
    private static void heapify(int[] arr, int pos, int size) {
        int left = pos * 2 + 1;
        while (left < size) {
            int larger = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            larger = arr[larger] > arr[pos] ? larger : pos;
            if (larger == pos) {
                break;
            }

            swap(arr, larger, pos);
            pos = larger;
            left = pos * 2 + 1;
        }
    }

    public static int[] maxTopK22(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) return null;

        int N = arr.length;
        if (k >= N) {
            Arrays.sort(arr);
            return arr;
        }

        //构建大根堆
        for (int i = N / 2 - 1; i < N; i++) {
            heapify11(arr, i, N);
        }

        int heapSize = N;
        int cnt = 1;
        swap(arr, 0, --heapSize);
        while (cnt < k && heapSize > 0) {
            heapify11(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            cnt++;
        }
        int[] ans = new int[k];

        for (int i = N - 1, j = 0; j < k; j++, i--) {
            ans[j] = arr[i];
        }
        return ans;
    }

    private static void heapify11(int[] arr, int pos, int heapSize) {
        int left = pos * 2 + 1;
        while (left < heapSize) {
            int right = left + 1;
            int large = right < heapSize && arr[left] < arr[right] ? right : left;
            large = arr[large] > arr[pos] ? large : pos;
            if (large == pos) {
                break;
            }
            swap(arr, large, pos);
            pos = large;
            left = pos * 2 + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 快排 O(n + k*(logk)
    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int N = arr.length;
        k = Math.min(N, k);

        //第K大的数，也即是降序排列下第N - K + 1小的数
        //找到索引位置下标为N - K位置小的数，即第N - K + 1小的数 O(N)
        int num = minKth(arr, N - k);
        int[] ans = new int[k];
        int ind = 0;
        //收集大于第K大的数
        for (int i = 0; i < N; i++) {
            if (arr[i] > num) {
                ans[ind++] = arr[i];
            }
        }

        //收集第K大的数，可能有多个第K大的数，但一共只能收集k个
        //3，1，0，5，4，7，2，7，7，8，求Top3 - 7，7，8
        for (; ind < k; ind++) {
            ans[ind] = num;
        }

        //对ans排序 O(K*logK)
        Arrays.sort(ans);
        //再逆序
        for (int L = 0, R = k - 1; L < R; L++, R--) {
            swap(ans, L, R);
        }
        return ans;
    }

    public static int[] maxTopK33(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) return null;

        int N = arr.length;
        if (k >= N) {
            Arrays.sort(arr);
            return arr;
        }

        //索引位置N - k小的数，第k大的数
        int num = minKth(arr, N - k);
        int[] ans = new int[k];
        int ind = 0;
        for (int i = 0; i < N; i++) {
            if (arr[i] > num) {
                ans[ind++] = arr[i];
            }
        }

        while (ind < k) {
            ans[ind++] = num;
        }

        Arrays.sort(ans);
        //逆序
        for (int L = 0, R = k - 1; L < R; L++, R--) {
            swap(ans, L, R);
        }
        return ans;
    }

    //时间复杂度 O(N) 空间复杂度O(1)
    private static int minKth(int[] arr, int ind) {
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] range = null;

        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = parition(arr, L, R, pivot);
            if (ind < range[0]) {
                R = range[0] - 1;
            } else if (ind > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    private static int[] parition(int[] arr, int L, int R, int pivot) {
        int lessInd = L - 1;
        int moreInd = R + 1;

        int i = L;
        while (i < moreInd) {
            if (arr[i] < pivot) {
                swap(arr, i++, ++lessInd);
            } else if (arr[i] == pivot) {
                i++;
            } else {
                swap(arr, i, --moreInd);
            }
        }
        return new int[]{lessInd + 1, moreInd - 1};
    }

    private static int minIndth(int[] arr, int ind) {
        int L = 0;
        int R = arr.length - 1;

        int pivot = 0;
        int[] range = new int[2];
        while (L < R) {
            pivot = arr[L + (int)(Math.random() * (R - L + 1))];
            range = partition11(arr, L, R, pivot);
            if (ind < range[0]) {
                R = range[0] - 1;
            } else if (ind > range[1]) {
                L = range[1] + 1;
            } else  {
                return pivot;
            }
        }
        return arr[L];
    }

    private static int[] partition11(int[] arr, int l, int r, int pivot) {
        int lessInd = l - 1;
        int moreInd = r + 1;
        int i = l;
        while (l < moreInd) {
            if (arr[i] < pivot) {
                swap(arr, i++, ++lessInd);
            } else if (arr[i] == pivot) {
                i++;
            } else {
                swap(arr, i, --moreInd);
            }
        }
        return new int[] {lessInd + 1, moreInd - 1};
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
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

    // 生成随机数组测试
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始，没有打印出错信息说明测试通过");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                pass = false;
                System.out.println("出错了！");
                printArray(ans1);
                printArray(ans2);
                printArray(ans3);
                break;
            }
        }
        System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
    }
}
