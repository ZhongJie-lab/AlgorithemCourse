package class48_dp_outcome;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.com/problems/palindrome-partitioning-ii/
public class PalindromePartitioningII {
    //伪代码 str[i....] 至少分成几部分？每部分都是回文
//	public static int f(char[] str, int i) {
//		if (i == str.length) {
//			return 0;
//		}
//
//		//i...i
//		//i...i + 1
//		//i...i + 2
//		int next = Integer.MAX_VALUE;
//
//		for (int end = i; end < str.length; end++) {
//			if (str[i..end] 是回文串) {
//				next = Math.min(next, f(str, end + 1));
//			}
//			return next + 1;
//		}
//	}

    //从左往右尝试模型， 时间复杂度 O(n^2)，判断前缀是否是回文事先搞定写到checkMap，否则回是O(n^3)
    public static int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }

        int N = s.length();
        boolean[][] checkMap = setCheckMap(s);
        //dp[i] 表示从i到N-1，分出几个回文串
        int dp[] = new int[N + 1];

        dp[N] = 0;
        for (int i = N - 1; i >= 0; i--) {
            if (checkMap[i][N - 1]) {
                dp[i] = 1;
            } else {
                int cnt = Integer.MAX_VALUE;
                for (int j = i; j < N; j++) {
                    if (checkMap[i][j]) {
                        cnt = Math.min(cnt, dp[j + 1]);
                    }
                }
                dp[i] = cnt + 1; //加1是算上[i..j]这部分回文串
            }
        }
        return dp[0] - 1;
    }

    //返回其中一种结果
    public static List<String> minCutOneWay(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            int N = s.length();
            int[] dp = new int[N + 1];
            dp[N] = 0;
            boolean[][] checkMap = setCheckMap(s);

            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int cnt = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            cnt = Math.min(cnt, dp[j + 1]);
                        }
                    }
                    dp[i] = cnt + 1;
                }
            }

            //已经得到dp[0]这个答案，接下来回溯找结果
            for (int i = 0, j = 1; j <= N; j++) {
                if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                    ans.add(s.substring(i, j));
                    i = j;
                }
            }
        }
        return ans;
    }

    public static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            int N = s.length();
            int[] dp = new int[N + 1];
            dp[N] = 0;
            boolean[][] checkMap = setCheckMap(s);

            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int cnt = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            cnt = Math.min(cnt, dp[j + 1]);
                        }
                    }
                    dp[i] = cnt + 1;
                }
            }

            List<String> path = new ArrayList<>();
            process(s, 0, 1, checkMap, dp, path, ans);
        }
        return ans;
    }

    // s[0...i-1] 已经存到path里去
    // s[i...j-1] 考察是否是分出来的第一份
    // s[i...N-1] 找答案
    private static void process(String s, int i, int j, boolean[][] checkMap, int[] dp, List<String> path, List<List<String>> ans) {
        if (j == s.length()) {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                ans.add(copyStrings(path)); //必须复制List
                path.remove(path.size() - 1); //恢复现场
            }
        } else {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                process(s, j, j + 1, checkMap, dp, path, ans);
                path.remove(path.size() - 1);
            }
            //如果不是i...j-1第一份
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    //范围尝试
    private static boolean[][] setCheckMap(String s) {
        int N = s.length();
        boolean[][] checkMap = new boolean[N][N];

        for (int i = 0; i < N - 1; i++) {
            checkMap[i][i] = true;
            checkMap[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        }
        checkMap[N - 1][N - 1] = true;

        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                checkMap[i][j] = s.charAt(i) == s.charAt(j) && checkMap[i + 1][j - 1];
            }
        }
        return checkMap;
    }

    public static List<String> copyStrings(List<String> strs) {
        List<String> ans = new ArrayList<>();
        for (String str : strs) {
            ans.add(str);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s = null;
        List<String> ans2 = null;
        List<List<String>> ans3 = null;

        System.out.println("第二问，返回其中一种结果：开始");
        s = "abacbc";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        s = "aabccbac";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabaa";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("第二问，返回其中一种结果：结束");

        System.out.println("第三问，返回其中一种结果：开始");
        s = "cbbbcbc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

//        s = "aaaaaa";
//        ans3 = minCutAllWays(s);
//        for (List<String> way : ans3) {
//            for (String str : way) {
//                System.out.print(str + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        s = "fcfffcffcc";
//        ans3 = minCutAllWays(s);
//        for (List<String> way : ans3) {
//            for (String str : way) {
//                System.out.print(str + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
        System.out.println("第三问，返回其中一种结果：结束");


    }
}
