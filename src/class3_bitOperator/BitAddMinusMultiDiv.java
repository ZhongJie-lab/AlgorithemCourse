package class3_bitOperator;

//https://leetcode.com/problems/divide-two-integers
public class BitAddMinusMultiDiv {
    /**
     * a + b
     * 加法可以拆分成 异或^运算(无进位相加) a'和 与&运算的结果再左移1位得到进位信息 b'，两者再相加
     * 对a' + b' 再重复以上过程，直到&运算的结果是0为止，此时a'''就是最终结果
     */
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b; //a'
            b = (a & b) << 1; //b'
            a = sum;
        }
        return sum;
    }

    /**
     * a - b
     * a + (-b)
     */
    public static int minus(int a, int b) {
//        return add(a, ~b + 1);
        return add(a, getNeg(b));
    }

    /**
     * a * b
     * b从右往左，如果是1，a左移b所在的位，如果是0，继续
     * 遍历完所有的b的位，将结果相加
     *
     */
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {//判断当前b的最右的一位是否为1
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1; //b 无符号右移1位，进行下一轮判断
        }
        return res;
    }

    /**
     * a / b
     * 因为最小整数无法取绝对值，分类处理
     * 情况1. a 和 b都不是最小整数
     * 情况2. a 和 b 都是最小整数
     * 情况3. a 是最小整数
     * 情况4. b 是最小整数
     */
    public static int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        } else if (b == Integer.MIN_VALUE) {
            return 0;
        } else if (a == Integer.MIN_VALUE) {
            if (b == -1) { //-2147483648/-1，真实结果2147483648，溢出，int型无法表示，则题目规定返回int最大值
                return Integer.MAX_VALUE;
            } else {
                //分为两部分
                //(a + 1)/b = c
                //(a - (c * b))/b 得到补偿值
                //以上结果相加
                int c = div(add(a, 1), b);
                return add(c, div(minus(a, multi(c, b)), b));
            }
        } else {
            return div(a, b);
        }
    }

    /**
     * a / b
     * a, b都不是最小整数的情况
     * 如果是负数，取绝对值
     * 1. b向左移动到最接近a的位置，且不比a大，说明结果中该位是1
     * 2. b左移的结果是2的？次方 * b
     * 3. a 减掉上面b左移的结果得到 a
     * 4. 重复上面的步骤，直到a变为0
     *
     */
    private static int div(int a, int b) {
        int x = isNeg(a) ? getNeg(a) : a;
        int y = isNeg(b) ? getNeg(b) : b;
        int res = 0;
        //x右移和y左移效果一样
        //用x右移去够Y，为了避免Y左移到符号位上
        //从30开始尝试而不是31，是因为x是非负数，符号位是0，不必尝试右移31位
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            if ((x >> i) >= y) {
               res |= (1 << i);
               x = minus(x, y << i);
            }
        }
        //如果a，b一正一负，结果是负数，否则是正数
        return isNeg(a) ^ isNeg(b) ? getNeg(res) : res;
    }

    private static int getNeg(int num) {
        return add(~num, 1);
    }

    private static boolean isNeg(int num) {
        return num < 0;
    }

    public static void main(String[] args) {
        int a = -2147483648;
        int b = 2;
        System.out.println(divide(a, b));
        System.out.println(divide(3, Integer.MIN_VALUE));
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
    }
}
