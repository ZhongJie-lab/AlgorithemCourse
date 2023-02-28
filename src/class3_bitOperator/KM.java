package class3_bitOperator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//一个数组，保证有一种数出现K次，其它数出现M次，K < M, M > 1，找到那个出现K次的数
// 要求空间复杂度O(1) 时间复杂度O(n)
public class KM {
    public static int  findKTimesNum(int[] arr, int k, int m) {
        int[] tmp = new int[32];

        //tmp[0] 0位置的1出现了几个
        //tmp[i] i位置的1出现了几个
        for (int num : arr) { //每个数字，提取一遍它每一位的0,1
            for (int i = 0; i < 32; i++) {
                tmp[i] += ((num >> i) & 1);
//                if (((num >> i) & 1) != 0) {
//                    tmp[i]++;
//                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            /*if(tmp[i] % m != 0) { //这个要找的出现K次的数的第i位是1
                ans |= (1 << i);
//                ans = ans | (1 << i);
            }*/
            //进一步，如果那个数应该出现k次，但没有出现k次，打印-1
            if (tmp[i] % m == 0) continue;
            if (tmp[i] % m == k) {
                ans |= (1 << i);
            } else {
                return -1;
            }

            //注意：边界条件，如果就是0出现的次数不是k次
            if (ans == 0) {
                int cnt = 0;
                for (int num : arr) {
                    if (num == 0) cnt++;
                }
                if (cnt != k) return -1;
            }
        }
        return ans;

    }

    public static int test(int[] arr, int k, int m) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return  -1;
    }

    //用对数器测试

    /**
     *
     * @param maxKinds - 为了得到一共有几种数
     * @param range
     * @param k
     * @param m
     * @return
     */
    public static int[] randomArray(int maxKinds, int range, int k , int m) {
        int kTimesNum = randomNum(range);
        int kinds = (int)(Math.random() * maxKinds) + 2; //一共有几种数字，必须大于2种
        int[] arr = new int[k + (kinds - 1) * m];
        //应该出现k次的数真实出现的次数：下面用times替换k
        // int times = Math.random() < 0.5 ? k : ((int)(Math.random() * (m - 1)) + 1);

        //填充arr
        int index = 0;
        // 1.填充出现k次的数
        for (; index < k; index++) {
            arr[index] = kTimesNum;
        }
        kinds--;
        Set<Integer> set = new HashSet<>();
        set.add(kTimesNum);
        // 2. 填充余下的数，出现m次的
        while (kinds > 0) {
            int currNum = 0;
            do {
                currNum = randomNum(range);
            } while (set.contains(currNum));
            set.add(currNum);
            kinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = currNum;
            }
        }

        //打乱arr的元素
        for (int i = 0; i < arr.length; i++) {
            //获取随机位置j，i位置的元素和j位置的交换
            int j = (int) (Math.random() * arr.length);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        return arr;
    }

    //[-range, range]
    public static int randomNum(int range) {
        return ((int)(Math.random() * range) + 1) - ((int)(Math.random() * range) + 1);
    }

    public static void main(String[] args) {
        int maxKinds = 4;
        int range = -200;
        int testTime = 100000;
        int max = 9;
        System.out.println("Start testing .....");
        for (int i = 0; i < testTime; i++) {
            int a = (int)(Math.random() * max) + 1; //保证 1 ~ 9
            int b = (int)(Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            //保证 k < m
            if (k == m) m++;

            int[] arr = randomArray(maxKinds, range, k , m);

            int ans1 = test(arr, k, m);
            int ans2 = findKTimesNum(arr, k, m);

            if (ans1 != ans2) {
                System.out.println("ERROR!");
            }
        }
        System.out.println("End testing .....");
//        int arr[] = {3,3,-7,5,5,-7,5};
//        System.out.println(findKTimesNum(arr, 1, 2));

    }
}
