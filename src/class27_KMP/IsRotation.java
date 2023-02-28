package class27_KMP;

//判断str1和str2是否是旋转字符串
//abcdefg
//bcdefga
//cdefgab
//defgabc
//efgabcd
//fgabcde
//gabcdef
//abcdefg
public class IsRotation {
    public static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        String b2 = b + b;
        return getIndexOf(b2, a) != -1; //原理：如果b是a的旋转字符串，那么a一定是b+b的子串
    }

    public static int getIndexOf(String s, String m) {
        if (s.length() < m.length()) return -1;

        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int[] next = getNextArray(ms);
        int i = 0;
        int j = 0;
        while (i < ss.length && j < ms.length) {
            if (ss[i] == ms[j]) {
                i++;
                j++;
            } else if (next[j] != -1) { //j != 0
                j = next[j];
            } else {
                i++;
            }
        }

        return j == ms.length ? i - j : -1;
    }

    private static int[] getNextArray(char[] str) {
        if (str.length == 1) {
            return new int[] { -1 };
        }

        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;

        int pos = 2;
        int cn = 0;

        while (pos < next.length) {
            if (str[pos - 1] == str[cn]) {
                next[pos] = ++cn;
                pos++;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos] = 0;
                pos++;
            }
        }

        return next;
    }

    public static void main(String[] args) {
        String str1 = "zhongjie";
        String str2 = "jiezhong";
        System.out.println(isRotation(str1, str2));
    }
}
