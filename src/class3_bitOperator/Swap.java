package class3_bitOperator;

//不用任何额外的变量交换2个整数
//异或运算符合交换律和结合律
// a ^ b = b ^a
// a ^ (b ^ c) = (a ^ b) ^ c
// N ^ N = 0
// N ^ 0 = N
public class Swap {
    public static void main(String[] args) {
        int a = 19;
        int b = 63;

        System.out.println(a + " - " + b);

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a + " - " + b);
    }
}
