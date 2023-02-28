package class5_6_10_sort.mergesort;

public class MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int L, int R) {
        if (L == R) return;
        int mid = L + (R - L)/2;
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int p = 0, p1 = l, p2 = mid + 1;
        int[] tmp = new int[r - l + 1];
        while (p1 <= mid && p2 <= r) {
            tmp[p++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }

        while (p2 <= r) {
            tmp[p++] = arr[p2++];
        }
        for (int i = 0; i < p; i++) {
            arr[l + i] = tmp[i];
        }
    }

    private static void merge2(int[] arr, int l, int mid, int r) {
        int[] tmp = new int[r - l + 1];
        int p1 = l, p2 = mid + 1, p = 0;

        while (p1 <= mid && p2 <= r) {
            if (arr[p1] <= arr[p2]) {
                tmp[p++] = arr[p1++];
            } else {
                tmp[p++] = arr[p2++];
            }
        }
        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[p++] = arr[p2++];
        }
        for (int i = 0; i < p; i++) {
            arr[l + i] = tmp[i];
        }
    }

    public static void mergeSortNonRecursive(int[] arr) {
        if (arr == null || arr.length < 2) return;

        int N = arr.length;

        int step = 1; //步长，每次merge的子数组长度，从1开始
        while (step < N) {
            int L = 0; //当前左组的第一个位置
            while (L < N) {
                if (step >= N - L) { //说明剩下的数都不足以满足一个步长，那就不用merge
                    break;
                }
                int mid = L + step - 1;
                int R = mid + Math.min(step, N - mid - 1); //有可能超
                merge(arr, L, mid, R);
                L = R + 1;
            }
            if (step > N / 2) { //防止溢出
                break;
            }
            step *= 2;
        }
    }
}
