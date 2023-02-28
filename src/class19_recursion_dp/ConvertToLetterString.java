package class19_recursion_dp;

/*规定1和A对应、2和B对应、3和C对应...26和Z对应
那么一个数字字符串比如"111”就可以转化为:
"AAA"、"KA"和"AK"
给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class ConvertToLetterString {
    // str只含有字符0 ~ 9， 问有多少种组合方案
    // 1 - A, 2 - B,.... 26 - Z
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    //当前来到index位置的字符
    // [0...index - 1]无需过问
    // [index....]去转化
    // 返回最多组合方案
    private static int process(char[] str, int index) {
        if (index == str.length) { //当前已没有字符，说明找到一种组合结果
            return 1;
        }
        if (str[index] == '0') { //当前为0， 无法继续，说明之前的决定有问题
            return 0;
        }

        int ways = process(str, index + 1); //只取当前字符翻译
        //取当前和下一个字符一起翻译
        if (index < str.length - 1 && ((str[index] - '0') * 10 + str[index + 1] - '0') <= 26) {
            ways += process(str, index + 2);
        }
        return ways;
    }

    //从右往左的动态规划
    // dp[i] 就是i....有多少种转化方式
    public static int number2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        int[] dp = new int[N + 1];
        dp[N] = 1;

        char[] str = s.toCharArray();
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i < str.length - 1 && (str[i] - '0') * 10 + str[i + 1] - '0' <= 26) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }
        return dp[0];
    }

    public static int numbers222(String s) {
        if (s == null || s.length() == 0) return 0;
        int N = s.length();
        int[] dp = new int[N + 1];

        dp[N] = 1;

        char[] chars = s.toCharArray();

        for (int i = N - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                dp[i] = dp[i + 1];
                if (i < N - 1 && (s.charAt(i) - '0') * 10 + s.charAt(i + 1) - '0' <= 26) {
                    dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }

    public static int number22(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        int[] dp = new int[N  + 1];
        dp[N] = 1;

        char[] chars = s.toCharArray();
        for (int i = N - 1; i >= 0; i--) {
            if (chars[i] != '0') {
                dp[i] = dp[i + 1];
                if (i < N - 1 && (chars[i] - '0') * 10 + chars[i + 1] - '0' <= 26) {
                    dp[i] += dp[i + 2];
                }
            }
        }

        return dp[0];
    }

    //从左往右的动态规划
    // dp[i] 表示str[0.. i]有多少种转换方式
    public static int number3(String s) {
        if (s == null || s.length() == 0) return 0;

        char[] str = s.toCharArray();
        int N = str.length;
        if (str[0] == '0') { //注意：这里的递归含义，允许当前字符为0，所以要加这个判断
            return 0;
        }
        int[] dp = new int[N];

        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            if (str[i] == '0') { //要带着前面的字符一起转，且前面的字符不能等于0且不能大于2， 且拼完，要求str[0, i - 2]可被分解
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0) ) {
                    dp[i] = 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                int ways = dp[i - 1]; //单个字符转
                // 尝试2个字符转
                if (str[i - 1] != '0' && ((str[i - 1] - '0') * 10 + str[i] - '0') <= 26) {
                    ways += (i - 2) >= 0 ? dp[i - 2] : 1; //i - 2 小于0， 说明前面已经没字符了，那么就是这1种组合
                }
                dp[i] = ways;
            }
        }
        return dp[N - 1];
    }

    public static void main(String[] args) {
        int N = 30;
        int testTimes = 1000;

        System.out.println("start test .....");
        for (int i = 0; i < testTimes; i++) {
            int len = (int)(Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = number2(s);

            if (ans0 != ans1) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println("Oops");
                break;
            }

        }
        System.out.println("end test ........");

//        String s = "111";
//        System.out.println(number3(s));
    }

    private static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char)((int)(Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }
}
