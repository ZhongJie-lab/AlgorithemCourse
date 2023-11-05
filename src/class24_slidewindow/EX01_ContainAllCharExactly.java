package class24_slidewindow;

import java.util.Arrays;

//https://leetcode.cn/problems/permutation-in-string/
//给你两个字符串s1和s2，写一个函数来判断s2是否包含s1的排列，如果是返回开始的位置，否则返回-1
//也即，s1的排列之一是s2的子串
//"ab", "eidbaooo" 输出3，s2包含排列之一"ba"
public class EX01_ContainAllCharExactly {
    //滑动窗口 + hashmap
    // O(N)
    public static int containsExactly(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() < s1.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int M = s1.length();
        int N = s2.length();
        //准备hashmap，要寻找的字符表，窗口每命中一个字符，就减1
        int[] countMap = new int[256];
        for (int i = 0; i < M; i++) {
            countMap[str1[i]]++;
        }
        int all = M; //一共要找的字符个数，减到0说明找到
        int L = 0, R = 0;

        //让窗口初步形成
        for (; R < M; R++) {
            if (countMap[str2[R]]-- > 0){
                all--;
            }
//            countMap[str2[R]]--;
        }
        //窗口初步形成，看下一个位置之前，每次检查窗口是否有效
        //如果无效，窗口左边出一个，右边进一个
        for (; R < N; R++) {
            if (all == 0) {
                return R - M;
            }
            //右边进一个，看是否在map中，如果在map中是要找的字符，all--
            //countMap[str2[R]] > 0，说明一定在map中
            //countMap[str2[R]] == 0，一定不在map中，还每每被窗口统计过
            if (countMap[str2[R]]-- > 0) {
                all--;
            }
            //左边出一个，str2[R - M]是窗口最左边要出去的字符，看是否在map中
            //countMap[str2[R - M]] > 0，说明在map中，是要找的字符，扔掉了，又欠了 all++
            //countMap[str2[R - M]] == 0，也说明在map中，曾经被窗口统计过，如果不是要找的字符，在map中的统计值一定是 < 0
            if (countMap[str2[R - M]]++ >= 0) {
                all++;
            }
        }
        //最后要再做一次判断，因为是先判断，再走下一步，R来到结尾的情况还没判断
        return all == 0 ? R - M : -1;
    }

    //hashmap
    // O(N^2)
    public static int containExactly2(String str1, String str2) {
        char[] aim = str1.toCharArray();
        char[] str = str2.toCharArray();

        for (int L = 0; L <= str.length - aim.length; L++) {
            if (isContain(str, L, aim)) {
                return L;
            }
        }
        return -1;
    }

    private static boolean isContain(char[] str, int L, char[] aim) {
        int[] countMap = new int[256];
        for (int i = 0; i < aim.length; i++){
            countMap[aim[i]]++;
        }
        for (int i = 0; i < aim.length; i++) {
            if (countMap[str[L + i]]-- == 0) {
                return false;
            }
//            countMap[str[L + i]]--;
        }
        return true;
    }

    //暴力解，测试用
    public static int forTest(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return -1;
        }
        char[] aim = s1.toCharArray();
        Arrays.sort(aim);
        String aimSort = String.valueOf(aim);
        for (int L = 0; L < s2.length(); L++) {
            for (int R = L; R < s2.length(); R++) {
                char[] chars = s2.substring(L, R + 1).toCharArray();
                Arrays.sort(chars);
                String currSort = String.valueOf(chars);
                if (currSort.equals(aimSort)) {
                    return L;
                }
            }
        }
        return -1;
    }

    public static boolean checkInclusion(String s1, String s2) {

        return false;
    }

    public static String getRandomStr(int possibilities, int maxSize) {
        char[] ans = new char[(int) (Math.random() * maxSize) + 1];

        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char)((int)(Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strMaxSize = 20;
        int aimMaxSize = 10;
        int testTimes = 50000;
        System.out.println("test begin, test times: " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomStr(possibilities, strMaxSize);
            String aim = getRandomStr(possibilities, aimMaxSize);
            int ans1 = forTest(aim, str);
            int ans2 = containExactly2(aim, str);
            int ans3 = containsExactly(aim, str);
            if (ans1 != ans3 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(aim);
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
//            System.out.println(aim);
//            System.out.println(str);
//            System.out.println(ans3);

        }
        System.out.println("test end");
    }

}
