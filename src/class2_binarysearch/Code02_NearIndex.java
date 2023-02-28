package class2_binarysearch;

public class Code02_NearIndex {
    //在arr上，找满足>=value的最左位置
    //保证有序数组不为空
    public static int leftNearestIndex(int[] arr, int value) {
        int L = 0;
        int R = arr.length - 1;
        int index = 0;

        while (L <= R) {
            int mid = L + (R - L)/2;
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }

        return index;
    }

    public static int leftNearestIndex2(int[] arr, int value) {
        int mid = 0;
        int L = 0, R = arr.length - 1;
        int ans = 0;

        while (L <= R) {
            mid = L +(R - L) / 2;
            if (arr[mid] > value) {
                ans = mid;
                R = mid - 1;
            } else if (arr[mid] == value) {
                ans = mid;
                break;
            } else {
                L = mid + 1;
            }
        }
        return mid;
    }

    //保证有序数组不为空
    //在arr上，找满足<= value的最右的位置
    public static int rightNearestIndex(int[] arr, int value) {
        int L = 0;
        int R = arr.length - 1;
        int index = 0;

        while(L <= R) {
            int mid = L + (R - L)/2;
            if (arr[mid] <= value) {
                index = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }

        return index;
    }

    public static void main(String[] args) {

    }
}
