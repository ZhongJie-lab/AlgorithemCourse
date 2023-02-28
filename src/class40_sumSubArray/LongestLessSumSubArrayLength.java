package class40_sumSubArray;

/*
给定一个整数组成的无序数组arr，值可能正、可能负、可能0
给定一个整数值K
找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
返回其长度

解：预处理结构 + 可能性舍弃
*/
public class LongestLessSumSubArrayLength {
    //O(N)
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        //minSum[i]: 以i开头的子数组的最小累加和
        //minSumEnds[i]: 以i开头的子数组的最小累加和的结尾位置
        int[] minSum = new int[N];
        int[] minSumEnds = new int[N];

        minSum[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;

        for (int i = N - 2; i >= 0; i--) {
            if (minSum[i + 1]>= 0) {
                minSum[i] = arr[i];
                minSumEnds[i] = i;
            } else {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            }
        }

        int len = 0;
        int sum = 0;
        int end = 0; //迟迟扩不进来的开头位置，一开始一个数都没有扩进来

        //窗口 [i..end)
        for (int i = 0; i < N; i++) {
            //0： 从0位置开始，能获得的最小累加和，如果<= k, 再从end位置开始扩，直到 > k 或end来到数组末尾
            //i: 从i位置开始，能获得的最小累加和，如果<= k, 再从end位置开始扩，直到 > k 或end来到数组末尾
            //窗口右边界可以不回退，假如0位置能扩到13位置，当前从1位置开始，直接得到1-13的累加和，接着看14能不能被吸进来
            // 因为即便回退得到一个有效答案，也不会比现在的len更长
            while (end < N && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnds[end] + 1;
            }
            //收集答案
            len = Math.max(len, end - i);

            //即将i++
            if (end > i) {
                sum -= arr[i];
            } else { //i == end 窗口无法维持了， end不动的话i要错过去了，窗口的概念维持不住了
                end = i + 1;
            }
        }

        return len;
    }

    //for test O(N*logN)
    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 100000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthAwesome(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
