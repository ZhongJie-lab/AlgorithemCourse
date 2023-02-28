package class2_binarysearch;

public class Code01_BSExist {

    public static boolean exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) return false;

        int mid = 0;
        int L = 0;
        int R = sortedArr.length - 1;

        while (L < R) {
            mid = L + (R - L) /2;
            if (sortedArr[mid] == num) {
                return true;
            } else if (sortedArr[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return sortedArr[L] == num;
    }
}
