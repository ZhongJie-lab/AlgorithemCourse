package class2_binarysearch;

// 一个正整数如果能被 a 或 b 整除，那么它是神奇的。
// 给定三个整数 n , a , b ，返回第 n 个神奇的数字。
// 因为答案可能很大，所以返回答案 对 10^9 + 7 取模 后的值。
// 测试链接 : https://leetcode.cn/problems/nth-magical-number/
public class Code05_NthMagicalNumber {
    public static int nthMagicNumber(int a, int b, int n) {
        //二分
        //a，b的最小公倍数
        long lcm = (long)a / gcd(a, b) * b;
        long l = 0, r = (long) Math.min(a, b) * n;
        long ans = 0;
        //一直二分到 l == r, [0,m]正好有n个神奇的数，而且m就是那个神奇的数
        while (l <= r) {
            long m = (l + r) / 2;
            //在[0, m]上有几个神奇的数字 -> m / a + m / b - m / lcm
            if (m / a + m / b - m / lcm >= n) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return (int)(ans % 1000000007);
    }

    //最大公约数
    private static long gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        int a = 40000, b = 40000, n = 1000000000;
        System.out.println(nthMagicNumber(a, b, n));
    }
}
