package class15_unionfind;

//https://leetcode.cn/problems/number-of-provinces/
public class Code02_FriendCircles {
//    public int findCircleNum(int[][] isConnected) {
//
//    }

    //N * N的二维数组 代表N个人的朋友关系
    public static int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (isConnected[i][j] == 1) { //表示i和j互相认识
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets;
    }

    public static class UnionFind {
        private int[] parent; //用数组来表示，优化常数时间的复杂度 parent[i] = k 表示i的父亲是k
        private int[] size; //i所在的集合大小是多少，i是代表节点，size[i]才有意义，否则无意义
        private int[] help; //辅助结构
        private int sets; //一共有多少个集合

        public UnionFind (int N) {
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
            sets = N;
        }

        public int find(int i) {
            int j = 0;
            while (i != parent[i]) {
                help[j++] = i; //用help记录沿途的节点，为了后续的优化
                i = parent[i];  //我就来到我的父节点
            }
            while (--j >= 0) {
                parent[help[j]] = i; //偏平化，沿途节点都挂在父节点下面
            }
            return i;
        }

        public void union(int i, int j) {
            int p1 = find(i);
            int p2 = find(j);
            if (p1 == p2) return;

            if (size[p1] >= size[p2]) {
                parent[p2] = p1;
                size[p1] += size[p2];
            } else {
                parent[p1] = p2;
                size[p2] += size[p1];
            }
            sets--;
        }

        public int sets() {
            return sets;
        }
    }
}
