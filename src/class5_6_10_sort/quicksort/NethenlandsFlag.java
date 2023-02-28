package class5_6_10_sort.quicksort;

public class NethenlandsFlag {
    //arr[L...R]上，以arr[R]位置的数x做划分，左：<= x 右：> x
    //返回 x被交换后的位置
    public static int netherlandsFlag1(int[] arr, int L, int R) {
        if (L > R) return -1;
        if (L == R) return L;
        int ind = L; //指针ind指向第一个数，依次往后探索，直到R
        int lessEqualInd = L - 1;
        while (ind < R) {
            if (arr[ind] <= arr[R]) {
                swap(arr, ind, ++lessEqualInd); //如果，当前数<= x, 和小于等于区的下一个数交换
            }
            ind++;
        } //相当于小于等于区推着大于区走
        swap(arr, ++lessEqualInd, R); // 最后，arr[R]和 <=区间的后一个位置交换
        return lessEqualInd;
    }

    public static int netherlandsFlag3(int[] arr, int L, int R) {
        int lessEqInd = L-1;
        int ind = L;
        while (ind < R) {
            if (arr[ind] <= arr[R]) {
                swap(arr, ++lessEqInd, ind++);
            } else {
                ind++;
            }
        }
        swap(arr, ++lessEqInd, R);
        return lessEqInd;
    }

    //arr[L...R], 以arr[R]位置的数x做划分，左：< x，中：= x，右：> x
    //返回 2个元素的数组，{x的最左位置，x的最右位置}
    public static int[] netherlandsFlag2(int[] arr, int L, int R) {
        if (L > R) return new int[] {-1, -1};
        if (L == R) return new int[] {L, R};

        int lessInd = L-1;
        int moreInd = R;
        int ind = L; //指针ind指向第一个数，依次往后探索，直到碰到大于区

        while (ind < moreInd) {
            if (arr[ind] < arr[R]) { //如果，当前数< x, 和小于区的下一个数交换，来到下一个数
                swap(arr, ind, ++lessInd);
                ind++;
            } else if (arr[ind] == arr[R]) { //如果，当前数== x, 直接跳过
                ind++;
            } else { //如果，当前数> x, 和大于区的前一个数交换，指针不动，交换来的数再要考量
                swap(arr, ind, --moreInd);
            }
        }
        swap(arr, moreInd, R); // 最后，arr[R] 和 >区间的第一个位置交换
        return new int[] {lessInd + 1, moreInd};
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public int partition1(int[] arr, int L, int R) {
        if (L > R) return -1;
        if (L == R) return L;
        int lessEqInd = L - 1;
        int ind = L;
        while (ind < R) {
            if (arr[ind] <= arr[R]) {
                swap(arr, ind, ++lessEqInd); //ind所指向的数字放入小于等于区
            }
            ind++;
        }

        return lessEqInd;
    }

    public int[] partition2(int[] arr, int L, int R) {
        if (L > R) return new int[] {-1, -1};
        if (L == R) return new int[] {L, L};

        int lessInd = L - 1;
        int moreInd = R;
        int ind = L;
        while (ind < moreInd) {
            if (arr[ind] < arr[R]) {
                swap(arr, ind++, ++lessInd);
            } else if (arr[ind] == arr[R]) {
                ind++;
            } else {
                swap(arr, ind, --moreInd);
            }
        }

        swap(arr, moreInd, R);
        return new int[] {lessInd + 1, moreInd};
    }

    public int partition11(int[] arr, int L, int R) {
        if (L > R) return -1;
        if (L == R) return L;
        int ind = L;
        int lessEqInd = L - 1;
        while (ind < R) {
            if (arr[ind] <= arr[R]) {
                swap(arr, ++lessEqInd, ind);
            }
            ind++;
        }
        swap(arr, ++lessEqInd, R);
        return lessEqInd;
    }

    public int[] partition22(int[] arr, int L, int R) {
        if (L > R) return new int[] {-1, -1};

        if (L == R) return new int[] {L, R};

        int lessInd =  L - 1;
        int moreInd = R;
        int ind = L;
        while (ind < moreInd) {
            if (arr[ind] < arr[R]) {
                swap(arr, ++lessInd, ind++);
            } else if (arr[ind] == arr[R]) {
                ind++;
            } else {
                swap(arr, --moreInd, ind);
            }
        }
        swap(arr, moreInd, R);
        return new int[] {lessInd + 1, moreInd};
    }
}

