package class2_binarysearch;

// 给你一个 严格升序排列 的正整数数组 arr 和一个整数 k 。
// 请你找到这个数组里第 k 个缺失的正整数。
// 测试链接 : https://leetcode.cn/problems/kth-missing-positive-number/
public class Code04_KthMissingPositiveNumber {
    public static int findKthPositive(int[] arr, int k) {
        int N = arr.length;
        //二分，找到一个位置pos，左边正好缺k个数，且是最逼近的位置（断点）
        int l = 0, r = N - 1;
        int m = 0;
        int pos = N; //pos初始在最右的出界位置
        while ( l <= r) {
            m = (l + r) /2;
            if (arr[m] - (m + 1) >= k) { //[0,m]范围上缺几个数？arr[m] - (m + 1) -> 下标对应的值 - （下标 + 1）
                //缺的数字大于等于k个，往左找
                pos = m;
                r = m - 1;
            }  else { //缺的数字小于k个，往右找
                l = m + 1;
            }
        }
        //断点的前一个位置的数字是多少？
        int preVal = pos == 0 ? 0 : arr[pos - 1]; //pos为0, 说明缺的数字全部在arr的左侧
        //前一个位置缺几个数？
        int missCnt = preVal - pos; //preVal - ((pos - 1) + 1)
        //还缺 k - missCnt个数，则第k个缺的数就是preVal + 还缺的个数
        return  preVal + (k - missCnt);

        //思考：
        // 如果arr不缺任何数字（可认为缺的数字都在arr右侧），没有断点，则二分之后pos没有移动，一样能得到合理的答案，[[1,2,3,4]第6个缺失的数字？10 = 4 + 6 - 0
        // 如果要找的缺的数字都在arr左侧，如 99,150,200,问第8个缺失的数字，则二分之后pos为0，k本身就是要找的缺失的第k个数字
    }

    public static void main(String[] args) {
//        int[] arr = {1,2,3,4,5};
//        int[] arr = {99,110, 200};
        int[] arr = {1,3,4,7,13,29,50};
        int k = 25;
        System.out.println(findKthPositive(arr, k));
    }
}
