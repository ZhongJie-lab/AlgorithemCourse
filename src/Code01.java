public class Code01 {
    public static void main(String[] args) {
        // 32位
        int num = 83928328;

        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
//        Code01.print(1 << 9);
//        print(max);
//        print(min);
//        print(-1);
//        System.out.println(min);

        int a = 25272623;
//        int b = ~a;
//        print(a);
//        print(b);

//        int n = 907275;
//        int m = 132508897;
//        print(n);
//        print(m);
//        System.out.println("-----------");
//        print(n | m);
//        print(n & m);
//        print(n ^ m); //异或

        print(min);
        print(min >> 1); //带符号右移
        print(min >>> 1); //不带符号右移

        int x = 5;
        int y = -x;
        int z = (~x + 1); //取相反数
        System.out.println(y);
        System.out.println(z);

        int d = ~min + 1; //负整数最小取反还是自己，最小负整数没有对应的正整数
        System.out.println(min);
        System.out.println(d);

        int zero = 0;
        int zzero = ~zero + 1; //0的相反数，取反后加1，最高位溢出就不要了，所以还是0
        System.out.println(-zero);
        System.out.println(-zzero);
    }

    //求整数的二进制表示形式
    public static void print(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }
}
