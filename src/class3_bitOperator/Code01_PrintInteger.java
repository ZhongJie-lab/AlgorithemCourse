package class3_bitOperator;

public class Code01_PrintInteger {
    //打印整数的二进制形式
    public static void print(int n) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((n & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }
    public static void main(String[] args) {
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(Integer.MAX_VALUE);
//        print(Integer.MIN_VALUE);
//        print(Integer.MAX_VALUE);
//
//        print(-1);
//        print(1);

        int a = 12529;
        int b = 9273;
        print(a);
        print(b);
        print(a | b);
        print(a & b);
        print(a ^ b);
    }
}
