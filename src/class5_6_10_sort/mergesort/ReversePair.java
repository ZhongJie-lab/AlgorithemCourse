package class5_6_10_sort.mergesort;

//在一个数组中，任何一个前面的数a，和任何一个后面的数b，如果(a,b)是降序的，就称为逆序对。返回数组中所有的逆序对
public class ReversePair {
    public static int reversParis(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r) return 0;
        int mid = l + (r - l)/2;
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }


    //左组与右组，从小到大排列，都从后往前merge，先考察右
    //如果先拷贝右数，说明右边的数大，不会产生逆序对
    //如果先拷贝左数，说明左边的数大，产生逆序对，逆序对的个数是p2 - (mid + 1) + 1 = p2 - mid
    //如果左数右数一样大，先拷贝右数，左数和右边比较产生逆序对
    private static int merge(int[] arr, int l, int mid, int r) {
        int res = 0;
        int p = r - l, p1 = mid, p2 = r;
        int[] tmp = new int[r - l + 1];

        while (p1 >= l && p2 > mid) {
            res += arr[p1] > arr[p2] ? (p2 - mid) : 0;
            tmp[p--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
//            if(arr[p2] > arr[p1]) {
//                tmp[p--] = arr[p2--];
//            } else if (arr[p2] < arr[p1]) {
//                res += p2 - mid;
//                tmp[p--] = arr[p1--];
//            } else {
//                tmp[p--] = arr[p2--];
//            }
        }
        while (p1 >= l) tmp[p--] = arr[p1--];
        while (p2 > mid) tmp[p--] = arr[p2--];

        for (int i = 0; i < tmp.length; i++) {
            arr[l + i] = tmp[i];
        }
        return res;
    }

    // for test
    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
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
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (reversParis(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
