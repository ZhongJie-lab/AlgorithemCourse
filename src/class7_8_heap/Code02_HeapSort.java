package class7_8_heap;

public class Code02_HeapSort {
    //堆排序额外空间复杂度O(1) 时间复杂度O(N*logN)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        //创建大顶堆，2种方法
        //方法一：从上往下建堆，从数组第一个数开始，每次插入一个元素使得堆的规模变大，时间复杂度O(N*logN)
//        for (int i = 0; i < N; i++) {
//            heapInsert(arr, i);
//        }
        //方法二：从下往上键堆，从倒数第二层开始调整堆，ShiftDown，（最后一层，每棵子树已经是大根堆了）时间复杂度O(N)
        for (int i = N/2 - 1; i >= 0; i--) {
            heapify(arr, i, N);
        }

        //此时建堆完成，从对顶开始依次弹出元素，堆的规模减小，同时调整堆
        swap(arr, 0, --N);
        while (N > 0) {
            heapify(arr, 0, N);
            swap(arr, 0, --N);
        }
    }

    //向上调整 - 从0位置开始调整，保证数组不会越界
    private static void heapInsert(int[] arr, int pos) {
        while (pos > 0 && arr[pos] > arr[(pos - 1) / 2]) {
            swap(arr, pos, (pos - 1) / 2);
            pos = (pos - 1) / 2;
        }
    }

    //向下调整
    private static void heapify(int[] arr, int pos, int heapSize) {
        int left = pos * 2 + 1;
        while (left < heapSize) {
            int larger = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            larger = arr[pos] > arr[larger] ? pos : larger;
            if (pos == larger) {
                break; //不用再往下探索了
            }
            swap(arr, larger, pos);
            pos = larger;
            left = pos * 2 + 1;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {-37, 38, 25, -1, 38, -51, 49, -21, 34, 43, -61, -12, 4, 22, 36, -52, 1, 21, -21, -26, 23, -15, 13, 48, 55, -48, 5, 19, -15, -57, -18, -2, 25, -22, -18, 10 };
//        int[] arr = new int[] {-37, 38, 25, -1, 38, -51, 49, -21, 34, 43, -61, -12, 4, 22, 36, -52, 1, 21, -21, -26, 23, -15, 13, 48, 55, -48, 5, 19};
        heapSort(arr);
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
