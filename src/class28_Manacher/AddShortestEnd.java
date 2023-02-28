package class28_Manacher;

/*
一个字符串，至少要在后面加多少个字符才能把整个字符串变成回文串
思路：求必须包含原串最后一个字符的回文串最长有多长，其余字符的逆序加在原串后面就是回文串
abc12321 加cba

从左往右尝试，一旦某个中心把最后一个字符包住了，停，得到包含最后一个字符的最长回文串，则前面的字符的逆序加在后面，整个字符串就是回文
 */
public class AddShortestEnd {
    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) return null;

        char[] str = manacherString(s);
        int[] rArr = new int[str.length];
        int R = -1;
        int C = -1;
        int maxContainsEnd = -1; //带最后一个字符的最长回文直径

        for (int i = 0; i < str.length; i++) {
            rArr[i] = R > i ? Math.min(rArr[2 * C - i], R - i): 1;
            while (i - rArr[i] >= 0 && i + rArr[i] < str.length) {
                if (str[i - rArr[i]] == str[i + rArr[i]]) {
                    rArr[i]++;
                } else {
                    break;
                }
            }

            if (i + rArr[i] > R) {
                R = i + rArr[i];
                C = i;
            }
            if (R == str.length) {
                maxContainsEnd = rArr[i];
                break;
            }
        }

        char[] res = new char[s.length() - (maxContainsEnd - 1)];

        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[i * 2 + 1]; //对应到str的位置换算， 原串的s[0] -> str[1] -> 结果串的res[res.length - 1]
        }
        return String.valueOf(res);
    }

    private static char[] manacherString(String s) {
        char[] res = new char[s.length() * 2 + 1];
        char[] chars = s.toCharArray();
        int index = 0;

        for (int i = 0; i < res.length; i++) {
            res[i] = i % 2 == 0 ? '#' : chars[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str = "abcd123321";
        System.out.println(shortestEnd(str));
    }
}
