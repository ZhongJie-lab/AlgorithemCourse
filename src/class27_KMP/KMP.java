package class27_KMP;

public class KMP {
    //在str1中查找str2
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s1.length() < s2.length()) {
            return -1;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int x = 0;
        int y = 0;

        //如果有了next数组，就可以确定要从哪里开始比较
        int[] next = getNextArray(str2);
        while (x < s1.length() && y < s2.length()) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] != -1) { //相当于y > 0，y来到前缀的下一个字符，和s1发生不等的x位置再比较
                y = next[y];
            } else { //next[y] == -1，左移到头了，没有相等的前后缀了，换一个位置尝试
                x++;
            }
        }

        return y == s2.length() ? (x - y) : -1;
    }

    //求str，每个i位置的前缀和后缀相等的最长的长度，前缀和后缀都不能包括str[i]自己
    private static int[] getNextArray(char[] str) {
        if (str.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str.length];
        next[0] = -1;
        next[1] = 0;
        //已知i-1位置的答案，要求i位置的
        int i = 2; //目前在哪个位置求next数组的值
        //cn一开始是next[i - 1]的值，即代表最长前缀和后缀的长度，也代表前缀中下一个字符在哪儿
        // cn代表现在是哪个字符要和str[i - 1]比较
        // 如果相等，next[i]的值就是cn + 1
        // 如果不相等，确定cn位置对应的next数组的值，得到该位置最长前缀和后缀的长度，cn再跳到所代表前缀的下一个字符去和str[i - 1]比较
        // 直到和str[i - 1]相等，就是cnt + 1
        // 或者，直到cn没法再跳了， 那答案就是0
        int cn = 0; //当i是2的时候，i - 1位置是和0位置比较

        while (i < str.length) {
            if (str[i - 1] == str[cn]) {
                next[i] = ++cn;
                i++;
            } else if (cn > 0) {
                cn = next[cn]; //往左跳
            } else { //cn <= 0,没法再往左跳了
                next[i] = 0;
                i++;
            }
        }
        return next;
    }

    //for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int)(Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possiblities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000;

        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possiblities, strSize);
            String match = getRandomString(possiblities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println(match);
            }
        }
        System.out.println("test end");


        System.out.println(getIndexOf("abegg2323jgo3", "jgo"));
    }
}
