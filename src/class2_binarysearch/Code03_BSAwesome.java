package class2_binarysearch;

/**
 * 无序数组，任意2个相邻的数不相等
 * 找出局部最小
 * 局部最小定义：
 *  1. 0位置是局部最小，要求arr[0] < arr[1]
 *  2. N - 1 位置是局部最小，要求arr[N-2] > arr[N-1]
 *  3. i位置是局部最小，要求arr[i-1] > arr[i] < arr[i+1]
 */

public class Code03_BSAwesome {
    public static int find(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        int N = arr.length;
        if (N == 1 || arr[0] < arr[1]) return 0;
        if (arr[N - 2] > arr[N - 1]) return N - 1;

        //趋势：arr[0] < arr[1]且arr[N - 2] > arr[N - 1]，开头下降，最后上扬，相邻数不相等，则1 ~ N-1 之间必出现局部最小
        int L = 1, R = N - 2;

        while (L < R) {
            int mid = L + (R - L)/2;
            if (arr[mid] > arr[mid - 1]) { //左侧出现趋势
                R = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) { //右侧出现趋势
                L = mid + 1;
            } else {
                return mid;
            }
        }
        return L;
    }

    public static int find2(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        int N = arr.length;
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return 1;
        }

        int L = 0, R = N - 1;
        while (L < R) {
            int mid = L + (R - L) / 2;
            if (arr[mid] < arr[L]) {

            }
        }
        return -1;
    }
}
