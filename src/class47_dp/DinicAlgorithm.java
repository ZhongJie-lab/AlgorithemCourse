package class47_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

//最大网络流 - Dinic算法
//https://lightoj.com/problem/internet-bandwidth
//优化点一：补反向边 A B 100 -> A, B, 100; B, A, 0; A^1 = B; B^1 = A
//优化点二：建立高度数组
//优化点三：cur[] 已经走过的边，当前接下来可以走的边
public class DinicAlgorithm {
    public static class Edge {
        public int from;
        public int to;
        public int available;

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }

    //nums - 节点数
    //edges 所有边的集合
    public static class Dinic {
        private int N;
        private ArrayList<Edge> edges; //边的集合
        private ArrayList<ArrayList<Integer>> nexts; //所有节点的邻接边，nexts.get(i), i节点的邻接边
        private int[] depth; //高度数组
        private int[] cur; //当前可用边数组，cur[i]是节点i的邻接边集合中的，当前可用的某条边在邻接边集合中的编号

        public Dinic(int nums) {
            N = nums + 1;
            nexts = new ArrayList<>();
            for (int i = 0; i <= N; i++) { //0, 1, ... N 每一个节点
                nexts.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[N];
            cur = new int[N];
        }

        // 从u到v，流量是r
        public void addEdges(int u, int v, int r) {
            int m = edges.size(); //当前是m条边在edges集合中
            edges.add(new Edge(u, v, r));
            nexts.get(u).add(m);
            edges.add(new Edge(v, u, 0));
            nexts.get(v).add(m + 1);
        }

        //s -  始发；t - 目标
        public int maxFlow(int s, int t) {
            int  flow = 0;
            while (bfs(s, t)) { //只要还能到达 t，标记高度
                Arrays.fill(cur, 0);
                flow += dfs(s, t, Integer.MAX_VALUE);
                Arrays.fill(depth, 0);
            }
            return flow;
        }

        //当前来到s，可变
        // t是目标 不可变
        //收到任务r
        //收集到的流量，作为结果返回 ans一定小于等于r
        private int dfs(int s, int t, int r) {
            if (s == t || r == 0) { //已经到目标t了，或没任务为0，t不可达了
                return r;
            }
            int flow = 0;
            int f = 0;
            //去尝试没走过的边 -> cur[s]
            for (; cur[s] < nexts.get(s).size(); cur[s]++) {
                int ei = nexts.get(s).get(cur[s]); //s点的邻接边集合nexts.get(s)中的，第cur[s]个元素，对应边集合edges中的一条边edges.get(ei)
                Edge e = edges.get(ei);
                Edge o = edges.get(ei ^ 1); //反向边
                //e.to是下一层 && 从e.to出发能到达t，并且收集到流量
                if (depth[e.to] == depth[s] + 1 && (f = dfs(e.to, t, Math.min(e.available, r))) != 0) {
                    e.available -= f;
                    o.available += f;
                    //收集流量
                    flow += f;
                    //剩余任务
                    r -= f;
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }

        private boolean bfs(int s, int t) {
//            depth[s] = 0; 初始化时已经暗含
            LinkedList<Integer> queue = new LinkedList<>();
            queue.addFirst(s);
            boolean[] visited = new boolean[N];
            visited[s] = true;

            while (!queue.isEmpty()) {
                int u = queue.pollLast();
                for (int i = 0; i < nexts.get(u).size(); i++) { //遍历邻接边
                    Edge e = edges.get(nexts.get(u).get(i));
                    int v = e.to;
                    if (!visited[v] && e.available > 0) {
                        visited[v] = true;
                        depth[v] = depth[u] + 1;
                        if (v == t) {
                            break;
                        }
                        queue.addFirst(v);
                    }
                }
            }
            return visited[t];
        }
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt(); //节点数
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt(); //边的数目
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdges(from, to, weight);
                dinic.addEdges(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }

}
