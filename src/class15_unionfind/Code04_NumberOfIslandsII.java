package class15_unionfind;

import java.util.ArrayList;
import java.util.List;

//https://leetcode.cn/problems/number-of-islands-ii/
public class Code04_NumberOfIslandsII {
    //m * n的空间，position是 k * 2的二维数组，表示该位置放’1‘，求每空降一个位置能得到的岛屿数量
    //思路：动态初始化，动态union，size表记录每个节点所在集合的大小，不只是代表节点会记录，所有节点都会记录
    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> ans = new ArrayList<>();
        UnionFind uf = new UnionFind(m, n);
        for (int[] pos : positions) {
            ans.add(uf.connect(pos[0], pos[1]));
        }
        return ans;
    }

    public static class UnionFind {
        int[] parents;
        int[] size;
        int[] help;
        int sets;
        int rows, cols;

        public UnionFind(int m, int n) {
            rows = m;
            cols = n;
            parents = new int[m * n];
            size = new int[m * n];
            help = new int[m * n];
            sets = 0;
        }

        public int find(int i) {
            int j = 0;
            while (i != parents[i]) {
                help[j++] = i;
                i = parents[i];
            }

            j--;
            while (j >= 0) {
                parents[help[j]] = i;
                j--;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == rows || c1 < 0 || c1 == cols || r2 < 0 || r2 == rows || c2 < 0 || c2 == cols) {
                return;
            }

            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) { //有一个点没有空降过’1‘, 没得union
                return;
            }

            int p1 = parents[i1];
            int p2 = parents[i2];
            if (p1 != p2) {
                if (size[p1] >= size[p2]) {
                    size[p1] += size[p2];
                    parents[p2] = p1;
                } else {
                    size[p2] += size[p1];
                    parents[p1] = p2;
                }
                sets--;
            }
        }

        //空降[r, c]之后能生成几个岛
        public int connect(int r, int c) {
            int ind = index(r, c);
            if (size[ind] == 0) { // ==0说明没有空降过，否则已经空降过
                parents[ind] = ind;
                size[ind] = 1;
                sets++;

                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

        private int index(int r, int c) {
            return r * cols + c;
        }
    }

    public static void main(String[] args) {
        int m = 3, n = 3;
        int[][] positions = {
                {0, 0},
                {0, 1},
                {1, 2},
                {2, 1}
        };

        List<Integer> integers = numIslands2(m, n, positions);
        integers.forEach(System.out::println);
    }
}
