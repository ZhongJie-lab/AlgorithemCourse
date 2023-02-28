package class5_6_10_sort.mergesort;

//在一个数组中，对于每个数num，求有多少个后面的数*2 依然< num, 求总个数
//比如 [3,1,7,0,2]
//3的后面有：1，0
//1的后面有：0
//7的后面有：0，2
//0的后面没有
//2的后面没有
//所以总共有5个
//不回退的算法
public class BiggerThanRightTwice {
    public static int biggerThanRightTwice(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) return 0;

        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    //左边与右边，从前往后merge
    //考察左边的组
    //在右边的组，设置一个指针windowR，表明当前到达的比左数*2还要小的位置的下一个数，[mid+1, windowR)
    private static int merge(int[] arr, int l, int mid, int r) {
        int res = 0;
        int[] tmp = new int[r - l + 1];
        int p = 0, p1 = l, p2 = mid + 1;
        int windowR = mid + 1;
        for (int i = l; i <= mid; i++) {
            while (windowR <= r && arr[i] > arr[windowR] * 2) {
                windowR++;
            }
            res += windowR - mid - 1; //windowR - (mid + 1)
        }

        while (p1 <= mid && p2 <= r) {
            tmp[p++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[p++] = arr[p2++];
        }

        for (int i = 0; i < tmp.length; i++) {
            arr[l + i] = tmp[i];
        }
        return res;
    }

    private static int merge2(int[] arr, int l, int mid, int r) {
        int p = 0, p1 = l, p2 = mid + 1;
        int winR = mid + 1;
        int ans = 0;
        int[] tmp = new int[r - l + 1];

        for (int i = l; i <= mid; i++){
            while (winR <= r && arr[i] > arr[winR] * 2) {
                winR++;
            }
            ans += winR - (mid + 1);
        }

        while (p1 <= mid && p2 <= r) {
            tmp[p++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[p++] = arr[p2++];
        }

        for (int i = 0; i < tmp.length; i++) {
            arr[l + i] = tmp[i];
        }

        return ans;
    }
    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
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
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (biggerThanRightTwice(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
