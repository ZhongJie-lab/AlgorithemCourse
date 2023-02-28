package class5_6_10_sort;

import class5_6_10_sort.quicksort.PartitionAndQuickSort;
import util.TestTool;

public class Sort {
    //选择排序
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int N = arr.length;
        //0 ~ N - 1 中找到最小值
        //1 ~ N - 1
        //...
        //N - 2 ~ N - 1
        for (int i = 0; i < N - 1; i++) {
            int minIndx = i; //先把i标为值最小的元素的下标
            for (int j = i + 1; j < N; j++) { //把最小值的下标找出来
                minIndx = arr[j] < arr[minIndx] ? j : minIndx;
            }
            swap(minIndx, i, arr);
        }
    }

    //冒泡排序
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int N = arr.length;
        //end是右边界
        // 0 ~ N - 1
        // 0 ~ N - 2
        // ...
        // 0 ~ 2
        // 0 ~ 1
        //每一轮下来保证最大的数来到0 ~ end的最后
        for (int end = N - 1; end > 0; end--) {
            for (int second = 1; second <= end; second++) { //两两比较，second是参与比较的第2个数
                if (arr[second - 1] > arr[second]) {
                    swap(second - 1, second, arr);
                }
            }
        }
    }

    //插入排序
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int N = arr.length;
        //i是右边界, 每一轮下来保证0 ~ i有序
        //0 - 1
        //0 - 2
        //0 - i
        //0 - N - 1
        for (int i = 1; i < N; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) { //0 - i-1 的数比较
                swap(j, j+1, arr);
            }
        }
    }

    //归并排序
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int[] tmp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, tmp);
    }

    public static void mergeSort(int[] arr, int l, int r, int[] tmp) {
        if (l == r) return;
        int mid = l + (r - l)/2;
        mergeSort(arr, l, mid, tmp);
        mergeSort(arr, mid + 1, r, tmp);
        merge(arr, l, mid, r, tmp);
    }

    public static void merge(int[] arr, int l, int mid, int r, int[] tmp) {
        int ind = 0; //tmp数组的指针
        int p1 = l, p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                tmp[ind++] = arr[p1++];
            }else {
                tmp[ind++] = arr[p2++];
            }
        }
        while (p1 <= mid) {
            tmp[ind++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[ind++] = arr[p2++];
        }
        for (int i = 0; i < ind; i++) {
            arr[l + i] = tmp[i];
        }
    }

    //快速排序
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort(int[] arr, int l, int r) {
        if (l >= r) return;
        int k = arr[l]; //k一开始设为范围[l, r]的左边第一个数，拿k来划分
        int i = l, j = r; //左右指针, a[i] 和 a[j]始终是有一个等于k
        //当发生偶数次交换的时候， 用a[j] 去和k比较；当发生奇数次交换的时候，用a[i] 和k比较
        while (i != j) {
            //发生偶数次交换
            while(j > i && arr[j] >= k) {
                j--;
            }
            System.out.println(i + "-----" + j);
            swap(i, j, arr);
            //发生奇数次交换
            while (i < j && arr[i] <= k) {
                i++;
            }
            System.out.println(i + "-----" + j);
            swap(i, j, arr);
        } //处理完后，a[i] = k
        quickSort(arr, l, i - 1);
        quickSort(arr, i + 1, r);
    }

    private static void swap(int i, int j, int[] arr) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxSize = 100;
        int maxValue = 100;

        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = TestTool.generateRandomArray(maxSize, maxValue);
            TestTool.printArray(arr1);
            int[] arr2 = TestTool.copyArray(arr1);

//            selectionSort(arr1);
            //bubbleSort(arr1);
//            insertionSort(arr1);
//            mergeSort(arr1);
//            quickSort(arr1);
//            MergeSort.mergeSortNonRecursive(arr1);
            PartitionAndQuickSort.quickSort1(arr1);
//            Code02_HeapSort.heapSort(arr1);

            TestTool.comparator(arr2);

            if (!TestTool.isEqual(arr1, arr2)) {
                succeed = false;
                TestTool.printArray(arr1);
                TestTool.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Bad!");

    }
}
