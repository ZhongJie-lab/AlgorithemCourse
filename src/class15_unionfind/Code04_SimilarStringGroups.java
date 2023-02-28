package class15_unionfind;

// 如果交换字符串 X 中的两个不同位置的字母，使得它和字符串 Y 相等，
// 那么称 X 和 Y 两个字符串相似。如果这两个字符串本身是相等的，那它们也是相似的。
// 例如，"tars" 和 "rats" 是相似的 (交换 0 与 2 的位置)；
// "rats" 和 "arts" 也是相似的，但是 "star" 不与 "tars"，"rats"，或 "arts" 相似。
// 总之，它们通过相似性形成了两个关联组：{"tars", "rats", "arts"} 和 {"star"}。
// 注意，"tars" 和 "arts" 是在同一组中，即使它们并不相似。
// 形式上，对每个组而言，要确定一个单词在组中，只需要这个词和该组中至少一个单词相似。
// 给你一个字符串列表 strs。列表中的每个字符串都是 strs 中其它所有字符串的一个字母异位词。
// 请问 strs 中有多少个相似字符串组？
// 测试链接 : https://leetcode.cn/problems/similar-string-groups/
public class Code04_SimilarStringGroups {
    public static int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        UnionFind uf = new UnionFind(n);
        //0 vs 1, 0 vs 2, 0 vs 3, ..., 0 vs n - 1
        //1 vs 2, ..., 1 vs n - 1
        //n - 2 vs n - 1
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (uf.findFather(i) != uf.findFather(j)) {
                    int diff = 0;
                    for (int k = 0; k < m; k++) {
                        if (strs[i].charAt(k) != strs[j].charAt(k)) {
                            diff++;
                            if (diff > 2) break;
                        }
                    }
                    if (diff == 0 || diff ==  2) { //判断相似
                        uf.union(i, j);
                    }
                }
            }
        }

        return uf.sets;
    }

    public static class UnionFind {
        public int[] parents;
        public int[] helps;
        public int[] size;
        public int sets;

        public UnionFind(int n) {
            parents = new int[n];
            helps = new int[n];
            size = new int[n];
            sets = n;

            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int findFather(int i) {
            int j = 0;
            while (i != parents[i]) {
                helps[j++] = i;
                i = parents[i];
            }

            while (j > 0) {
                parents[helps[--j]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int p1 = findFather(i);
            int p2 = findFather(j);

            if (p1 != p2) {
                int s1 = size[p1];
                int s2 = size[p2];
                if (s1 > s2) {
                    parents[p2] = i;
                    size[p1] += size[p2];
                } else {
                    parents[p1] = j;
                    size[p2] += size[p1];
                }
                sets--;
            }
        }

        public int size() {
            return sets;
        }
    }
}
