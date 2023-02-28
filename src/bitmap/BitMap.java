package bitmap;

import java.util.HashSet;

public class BitMap {
    private long[] bits; //数组中的每个数的每一位bit，用于表示某个数是否存存在

    /**
     * max：BitMap能表示的数字范围 0 ~ max
     * 64个数字可以用1个long类型的数表示
     * max个数需要 (max+64)/64 个long类型的数表示
     * 除以64 等于 右移6位
     */
    public BitMap(int max) { //设置数组的长度
        bits = new long[(max+64) >> 6];
    }

    /**
     * 把数字num加入到bitmap，哪个数的哪一位，把该数字所在的那一位变成1
     * 1. bitmap的哪个数？ num >> 6
     * 2. 那个数的第几位？ num % 64 等于 num & 63
     * 3. 1左移 到那一位，再|进去
     */
    public void add(int num) {
        bits[num >> 6] |= (1L << (num & 63));
    }

    /**
     * 从bitmap中删除一个数，把该数字所在的那一位变成0
     * 1. bitmap中的哪个数？ num >> 6
     * 2. 第几位？ num & 63
     * 3. 让那一位变成0：1左移到那一位，取反，再&运算，把那一位变成0
     */
    public void delete(int num) {
        bits[num >> 6] &= ~(1L << (num & 63));
    }

    /**
     * 判断数字num是否存在
     * 1. 在bitmap中定位 num >> 6
     * 2. 第几位？ num & 63
     * 3. &运算，其它位都置为0，看那一位如果是0，结果就是0就不存在，那一位如果是1，结果就是非0就说明存在
     */
    public boolean contains(int num) {
        return (bits[num >> 6] & (1L << (num & 63))) != 0;
    }

    public static void main(String[] args) {
        System.out.println("测试开始！");
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        HashSet<Integer> set = new HashSet<>();
        int testTime = 10000000;
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.delete(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("Oops!");
                    break;
                }
            }
        }
        for (int num = 0; num <= max; num++) {
            if (bitMap.contains(num) != set.contains(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }
}
