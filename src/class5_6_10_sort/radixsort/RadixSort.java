package class5_6_10_sort.radixsort;

//基数排序
public class RadixSort {
    //only for non-negative value
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;

        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    private static void radixSort(int[] arr, int L, int R, int digit) {
        int radix = 10; // 一共10个桶
        int[] help = new int[R - L + 1];

        for (int d = 1; d <= digit; d++) { //从最低位开始遍历，一直到最高位
            int[] count = new int[radix]; //准备10个桶，下标代表桶表示的数字
            int n = 0;
            //遍历原数组的每一个数
            for (int i = L; i <= R; i++) {
                n = getDigit(arr[i], d); //获取d位上的数字
                count[n]++;
            }
            //遍历桶，累加以后，每个桶表示 该位 <= 当前桶表示的数字的个数
            for (int i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }
            //从右往左遍历原数组，当前位的数 在桶中的值表示 <= ,  这些数应占据0到count[n] - 1的位置，放到最右
            for (int i = R; i >= L; i--) {
                n = getDigit(arr[i], d);
                help[count[n] - 1] = arr[i];
                count[n]--; // 把位置个数减一
            }
            //复制回原数组
            for (int i = 0; i < help.length; i++) {
                arr[i + L] = help[i];
            }
        }
    }

    //获得num在d位上的数
    private static int getDigit(int num, int d) {
       return ((int)(num / (Math.pow(10, d - 1)))) % 10;
    }

    //位数最多的数字
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int n : arr) {
            max = Math.max(max, n);
        }

        int digit = 0;
        while (max != 0) {
            digit++;
            max /= 10;
        }
        return digit;
    }
}
