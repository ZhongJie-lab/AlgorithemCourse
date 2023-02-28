package class16_graph;

import java.util.ArrayList;
import java.util.List;

public class CanMeeting {
    // 村民编号0~n-1, 一共n个人
    // hates[i] = {a, b} 表示a和b有矛盾
    // 返回能不能分成两组开会
    public static boolean canMeeting(int n, int[][] hates) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] hate : hates) {
            graph.get(hate[0]).add(hate[1]);
            graph.get(hate[1]).add(hate[0]);
        }
        UnionFind uf = new UnionFind(n);
        for (ArrayList<Integer> neighbours : graph) {
            for (int i = 1; i < neighbours.size(); i++) {
                uf.union(neighbours.get(i - 1), neighbours.get(i));
            }
        }
        for (int[] hate : hates) {
            if (uf.same(hate[0], hate[1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 4;
        int[][] hates = {
                {0, 1},
                {1, 2},
                {2, 3},
                {0, 3}
        };

        int[][] graph = {
                {1, 3},
                {0, 2},
                {1, 3},
                {0,2}
        };

        System.out.println(canMeeting(n, hates));
        System.out.println("-------------------------------------------");
        System.out.println(isBipartite2(graph));
    }

    //https://leetcode.cn/problems/is-graph-bipartite/
    /*
    输入：graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
    输出：false

    输入：graph = [[1,3],[0,2],[1,3],[0,2]]
    输出：true
     */
    public static boolean isBipartite(int[][] graph) {
        int n = graph.length;
        UnionFind uf = new UnionFind(n);
        for (int[] neighbors : graph) {
            for (int i = 1; i < neighbors.length; i++) {
                uf.union(neighbors[i - 1], neighbors[i]);
            }
        }

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (uf.same(i, graph[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isBipartite2(int[][] graph) {
        int n = graph.length;
        UnionFind uf = new UnionFind(n);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (uf.same(i, graph[i][j])) {
                    return false;
                }
                uf.union(graph[i][0], graph[i][j]);
            }
        }

        return true;
    }

    public static class UnionFind {
        private int[] f;
        private int[] s;
        private int[] h;
        private int sets;
        public UnionFind(int n) {
            f = new int[n];
            s = new int[n];
            h = new int[n];
            for (int i = 0; i < n; i++) {
                f[i] = i;
                s[i] = 1;
            }
            sets = n;
        }

        private int find(int i) {
            int hi = 0;
            while (i != f[i]) {
                h[hi++] = i;
                i = f[i];
            }
            while (hi > 0) {
                f[h[--hi]] = i;
            }
            return i;
        }

        public boolean same(int i, int j) {
            return find(i) == find(j);
        }

        public void union(int i, int j) {
            int fi = find(i);
            int fj = find(j);
            if (fi != fj) {
                if (s[fi] >= s[fj]) {
                    f[fj] = fi;
                    s[fi] = s[fi] + s[fj];
                } else {
                    f[fi] = fj;
                    s[fj] = s[fi] + s[fj];
                }
                sets--;
            }

        }

        public int sets() {
            return this.sets;
        }
    }
}
