package class28_Manacher;

/*假设字符串str长度为N，想返回最长回文子串的长度
时间复杂度O(N)
 */

public class Manacher {
    // 为了好计算偶数回文串，需要在每个字符前后加一个特色字符
    // 回文半径数组
    // R 最右回文边界
    // C 与之对应的中心

    //Case1 i没有被R罩住              暴力扩
    //Case2 i被R罩住了 (L   i'    C    i   R)
    //case2.1  i'的回文区域在L,R内     i'的回文半径就是i的回文半径
    //case2.2  i'的回文区域超出了L,R    i'的回文半径就是i到R的距离
    //case2.3  i'的回文左半径正好压着L  i'的回文半径至少是i到R的距离，再尝试往外扩
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int max = 0;

        char[] str = manacherString(s);
        int[] rArr = new int[str.length];
        int R = -1; //R是回文区域到不了的边界，开区间
        int C = -1;
        //rArr[2 * C - i]   -   i'的回文半径
        for (int i = 0; i < str.length; i++) {
            //初始化rArr[i]
            rArr[i] = R > i ? Math.min(rArr[2 * C - i], R - i) : 1;
            while (i + rArr[i] < str.length && i - rArr[i] >= 0) {
                if (str[i + rArr[i]] == str[i - rArr[i]]) {
                    rArr[i]++;
                } else {
                    break;
                }
            }
            //更新R， C
            if (i + rArr[i] > R) {
                R = i + rArr[i];
                C = i;
            }
            max = Math.max(max, rArr[i]);
        }

        return max - 1; //原串的回文直径就是str串的回文半径减一
    }

    private static char[] manacherString(String s) {
        char[] manacherChars = new char[s.length() * 2 + 1];
        char[] chars = s.toCharArray();
        int index = 0;

        for (int i = 0; i < manacherChars.length; i++) {
            manacherChars[i] = (i % 2 == 0) ? '#' : chars[index++];
        }
        return manacherChars;
    }

    //for test
    public static int right(String s) {
        if (s == null || s.length() == 0) return 0;

        char[] str = manacherString(s);
        int max = 0;

        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;

            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }

        return max / 2;
    }

    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int)(Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char)((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 10000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("end test");
    }
}
