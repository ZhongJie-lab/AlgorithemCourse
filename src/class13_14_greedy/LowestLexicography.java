package class13_14_greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

//给定一个字符串字组成的数组strs，必须把所有字符拼接起来，返回所有可能的拼接结果中，字典序最小的结果
//["abc", "cks", "ft"]
//["b", "ba"] b ba, ba b
//错误的贪心，把所有字符串根据字典序排序以后拼接起来，但不是的 if (a < b) a前 else b前
//正确的贪心，if (a.b < b.a) a前 else b前
//传递性 a.b <= b.a, b.c <= c.b , 可以推导出 a.c <= c.a
public class LowestLexicography {
    //贪心算法
    public static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1 + o2).compareTo(o2 + o1);
            }
        });

        StringBuilder res = new StringBuilder();
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }


    //暴力递归求解
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    //所有拼接结果，按顺序排列的集合
    private static TreeSet process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();

        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] rests = removeIndexString(strs, i);
            TreeSet<String> restsSet = process(rests);
            for (String s : restsSet) {
                ans.add(first + s);
            }
        }
        return ans;
    }

    private static String[] removeIndexString(String[] strs, int ind) {
        int N = strs.length;
        String[] ans = new String[N - 1];
        int j = 0;
        for (int i = 0; i < strs.length; i++) {
            if (i != ind) {
                ans[j++] = strs[i];
            }
        }
        return ans;
    }


}
