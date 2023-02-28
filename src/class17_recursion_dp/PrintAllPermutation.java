package class17_recursion_dp;

import java.util.ArrayList;
import java.util.List;

public class PrintAllPermutation {
    public static List<String> permutation(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) return ans;

        char[] str = s.toCharArray();

        process(str, 0, ans);
        return ans;
    }

    private static void process(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        //始终和后面的字符交换
        for (int i = index; i < str.length; i++) {
            swap(str, index, i);
            process(str, index + 1, ans);
            swap(str, index, i); //恢复现场
        }
    }

    private static void process11(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }

        for (int i = index; i < str.length; i++) {
            swap(str, index, i);
            process(str, index + 1, ans);
            swap(str, index, i);
        }
    }

    //去重
    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) return ans;

        char[] str = s.toCharArray();
        process2(str, 0, ans);
        return ans;
    }

    private static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        boolean[] visited = new boolean[256]; //ascii码表示字母, 作为数组下标. 为true说明i位置的字符已经做了排列，跳过
        for (int i = index; i < str.length; i++) {
            if (!visited[str[i]]) {
                visited[str[i]] = true;
                swap(str, i, index);
                process2(str, index + 1, ans);
                swap(str, i, index);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }

    public static void main(String[] args) {
        List<String> ans = permutation2("acc");
        ans.forEach(System.out::println);
    }
}
