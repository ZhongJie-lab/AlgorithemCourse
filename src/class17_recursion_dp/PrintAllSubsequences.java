package class17_recursion_dp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PrintAllSubsequences {
    public static List<String> subs(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        String path = "";
        char[] str = s.toCharArray();
        process(str, 0, path, ans);

        return ans;
    }

    //每个字符 - 选，不选
    private static void process(char[] str, int index, String path, List<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        //不要当前字符
        process(str, index + 1, path, ans);
        //要当前字符
        process(str, index + 1, path + str[index], ans);
    }

    public static List<String> subsNoRepeat(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        HashSet<String> ans = new HashSet<>();
        String path = "";
        char[] str = s.toCharArray();
        process2(str, 0, path, ans);

        return new ArrayList<>(ans);
    }

    private static void process2(char[] str, int index, String path, HashSet<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        process2(str, index + 1, path, ans);
        process2(str, index + 1, path + str[index], ans);
    }

    public static void main(String[] args) {
        List<String> ans = subs("abc");
        List<String> res = subsNoRepeat("abbc");
        ans.forEach(System.out::println);
        System.out.println("-----------------------");
        res.forEach(System.out::println);
    }
}
