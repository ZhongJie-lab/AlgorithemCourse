package class19_recursion_dp;

import java.util.HashMap;
import java.util.Map;

//https://leetcode.cn/problems/stickers-to-spell-word/
public class StickersToSpellWord {
    //暴力递归
    public static int minStickers1(String[] stickers, String target) {
        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    //所有贴纸stickers，每一张贴纸都有无穷张
    // target 要拼出的
    // 返回至少要多少张
    private static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;

        for (String first : stickers) {
            String rest = minus(target, first); //看first这张贴纸能拿出多少字符帮target
            if (rest.length() != target.length()) { //==表示first没有贡献，如果first没有贡献，下面的process和当前一样的，无需进入
                min = Math.min(min, process(stickers, rest));
            }
        }

        return min == Integer.MAX_VALUE ? min : min + 1; //用了first这张贴纸，所以结果要加一
    }

    private static String minus(String target, String str) {
        char[] tChars = target.toCharArray();
        char[] sChars = str.toCharArray();
        int[] count = new int[26]; //题意只有小写字母

        //词频统计
        for (char c : tChars) {
            count[c - 'a']++;
        }
        for (char c : sChars) {
            count[c - 'a']--;
        }
        StringBuilder result = new StringBuilder();
        //遍历count
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                while (count[i] > 0) {
                    result.append((char) (i + 'a'));
                    count[i]--;
                }
            }
        }
        return result.toString();
    }

    //递归 - 剪枝
    //关键优化：用词频统计替代贴纸数组
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char chr : stickers[i].toCharArray()) {
                counts[i][chr - 'a']++;
            }
        }

        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }

        //target做出词频统计
        int[] tcounts = new int[26];
        char[] t = target.toCharArray();
        for (char chr : t) {
            tcounts[chr - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        int N = stickers.length;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            //最关键优化，重要的剪枝
            if(sticker[t[0] - 'a'] > 0) { //只考虑，贴纸中包含target第一个字符的，如果贴纸没有target第一个字符，就不用选这张贴纸了
                //选了这个贴纸，看对target的贡献，之后还剩下的
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int num = tcounts[j] - sticker[j];
                        while (num > 0) {
                            sb.append((char)(j + 'a'));
                            num--;
                        }
                    }
                }
                String rest = sb.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1;
    }

    //动态规划 可变参数是复杂类型，不能使用严格表结构，使用记忆化搜索
    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char chr : stickers[i].toCharArray()) {
                counts[i][chr - 'a']++;
            }
        }

        Map<String, Integer> dp = new HashMap<>(); //k - target; v - 需要几张贴纸
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process3(int[][] stickers, String target, Map<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }

        if (target.length() == 0) {
            return 0;
        }

        //target做出词频统计
        int[] tcounts = new int[26];
        char[] t = target.toCharArray();
        for (char chr : t) {
            tcounts[chr - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        int N = stickers.length;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            //最关键优化，重要的剪枝
            if(sticker[t[0] - 'a'] > 0) { //只考虑，贴纸中包含target第一个字符的，如果贴纸没有target第一个字符，就不用选这张贴纸了
                //选了这个贴纸，看对target的贡献，之后还剩下的
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int num = tcounts[j] - sticker[j];
                        while (num > 0) {
                            sb.append((char)(j + 'a'));
                            num--;
                        }
                    }
                }
                String rest = sb.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min == Integer.MAX_VALUE ? min : min + 1;
        dp.put(target, ans);
        return ans;
    }


    public static void main(String[] args) {
        String[] stickers = {
                "heavy","claim","seven","set","had","it","dead","jump","design","question","sugar","dress","any","special","ground","huge","use","busy","prove","there","lone","window","trip","also","hot","choose","tie","several","be","that","corn","after","excite","insect","cat","cook","glad","like","wont","gray","especially","level","when","cover","ocean","try","clean","property","root","wing"
        };
        String target = "travelbell";
        System.out.println(minStickers2(stickers, target));
    }
}
