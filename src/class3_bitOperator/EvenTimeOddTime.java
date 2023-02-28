package class3_bitOperator;

public class EvenTimeOddTime {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,1,4,5,5,6,2,3};
        System.out.println(findOneOddTimeNum(arr));

        int[] array = {1,2,3,4,1,4,5,5,6,2,3,3};
        findTwoOddTimesNum(array);

    }

    //数组保证只有1个数出现奇数次，其余出现偶数次
    public static int findOneOddTimeNum(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        return eor;
    }

    //数组保证只有2个数出现奇数次，其余数出现偶数次
    public static void findTwoOddTimesNum(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        // 假设出现奇数次的数是a和b，eor 的结果就是a^b的结果
        int t = eor & (-eor); //提取最右的1
        int a = 0;
        //此时，出现偶数次的数要么这一位是1，要么是0
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & t) != 0)  { //得到那些这一位是1的数
                a ^= arr[i];
            }
        }
        //最后的结果
        System.out.println(a + " " + (a ^ eor));
    }
}
