package class5_6_10_sort.mergesort;

//在一个数组中，一个数的左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组的小和。求数组小和
//等价于，数组中每一个数arr[i]的右边比它大的数的个数n，smallSum += n*arr[i]
public class SmallSum {
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int mid = l + (r - l) /2;
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    //左组与右组，merge的时候，先考察右
    //如果先拷贝的是右边的数，意味着右边的数小，因此就不产生小和
    //如果先拷贝的是左，意味着左边的数小，可求出右边有多少个数比这个数大，n*这个数
    //如果左数和右数相等：
    // 先拷贝右边的数，让右边的指针往下走，走到能分出大小的时候，
    // 再拷贝左边的数，此时，才知道右边有多少个数比左边这个数大
    private static int merge(int[] arr, int l, int mid, int r) {
        int[] tmp = new int[r - l + 1];
        int p = 0, p1 = l, p2 = mid + 1;
        int res = 0;

        while (p1 <= mid && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                res += (r - p2 + 1) * arr[p1];
            }
            tmp[p++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }
        while (p2 <= r) {
            tmp[p++] = arr[p2++];
        }

        for (int i = 0; i < tmp.length; i++) {
            arr[l+i] = tmp[i];
        }
        return res;
    }

    public static int smallSum11(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        return process11(arr, 0, arr.length - 1);
    }

    private static int process11(int[] arr, int l, int r) {
        if (l == r) return 0;
        int mid = l + (r - l) / 2;
        return process11(arr, l, mid) +
                process11(arr, mid + 1, r) +
                merge11(arr, l, mid, r);
    }

    private static int merge11(int[] arr, int l, int mid, int r) {
        int[] tmp = new int[r - l + 1];
        int p = 0, p1 = l, p2 = mid + 1;

        int ans = 0;

        while (p1 <= mid && p2 <= r) {
            ans += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
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


    private static int merge2(int[] arr, int l, int mid, int r) {
        int p = 0, p1 = l, p2 = mid + 1;
        int ans = 0;
        int[] tmp = new int[r - l + 1];

        while (p1 <= mid && p2 <= r) {
            if (arr[p1] < arr[p2]) {
                ans += arr[p1] * (r - p2 + 1);
            }
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

    //for test - O(n^2)
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
    }

    //for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)((maxValue + 1) * Math.random());
        }
        return arr;
    }

    //for test
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

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);

            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Opps!");
    }
}
