package class3_bitOperator;

//提取最右侧的1的

/**
 * 11101010
 * 00000010
 * 异或运算也称为无符号加法
 */
public class FindMostRightOne {
    public static void main(String[] args) {
        int num = 98;
        /**
         * num和自己的相反数做&运算
         * 解释：
         * 相反数就是取反加1
         *  左边都是相反数，与的结果都是0；右边由于进位都是0，与的结果也就是0，也就是只剩下最右边的1了
         */
        int ans = num & (~num + 1); //num & (-num)

        get(num);
        get(ans);
//        for (int i = 31; i >= 0; i--) {
//            System.out.print(((1 << i) & ans) == 0 ? "0" : "1");
//        }
    }

    public static void get(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print(((1 << i) & num) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}
