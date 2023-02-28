package class16_graph;

// 最小生成树：
// 图G的生成树是一棵包含G的所有顶点的树（所有点都联通），树上所有权值总和表示代价，那么在G的所有生成树中代价最小的生成树称为图G的最小生成树

import class16_graph.graph.Edge;
import class16_graph.graph.Graph;
import class16_graph.graph.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/*
    1）可以从任意节点出发寻找最小生成树
    2）某个点加入到备选去的点中后，解锁这个点出发的所有新的边
    3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
    4）如果会，不要当前边，继续考察剩下解锁边中的最小的边，重复3）
    5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
    6）当所有点都被选取，最小生成树就得到了
 */
public class Prim {
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });
        HashSet<Node> visited = new HashSet<>();

        Set<Edge> result = new HashSet<>();

        for (Node node : graph.nodes.values()) { //随便挑一个点
            //node 是开始点，如果graph是连通图，一次下来就可以得到最小生成树了
            if (!visited.contains(node)) { //去重，防止成环
                visited.add(node);
                for (Edge edge : node.edges) {
                    priorityQueue.add(edge);
                }

                while (!priorityQueue.isEmpty()) {
                    Edge edge = priorityQueue.poll();
                    Node toNode = edge.to;
                    if (!visited.contains(toNode)) { //不含有时，是新的点
                        visited.add(toNode);
                        result.add(edge);
                        for (Edge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            //break 如果不是森林，这里就可以break了
        }
        return result;
    }

    //保证graph是连通图
    //graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    //要求返回最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length; //有多少个点
        int[] distances = new int[size];
        boolean[] visited = new boolean[size];

        for (int i = 0; i < size; i++) {
            distances[i] = graph[0][i]; //0到其他点的边的权值
        }
        //选择0为开始点
        int sum = 0;

        for (int i = 1; i < size; i++) { //已经选中0，最多再看剩余 size-1次
            int minPath = Integer.MAX_VALUE;
            int minIdex = -1;
            for (int j = 0; j < size; j++) { //选择距离最小的一条边
                if (!visited[j] && distances[j] < minPath) {
                    minPath = distances[j];
                    minIdex = j;
                }
            }
            if (minIdex == -1) { //说明左右点都访问过了，已找到最小路径
                return sum;
            }
            visited[minIdex] = true;
            sum += minPath;
            //继续解锁被选中边的另一个点的相邻边
            for (int j = 0; j < size; j++) {
                //0 - j的距离和 minIndex - j的距离PK，距离小的胜出 更新distances[j]
                if (!visited[j] && distances[j] > graph[minIdex][j]) {
                    distances[j] = graph[minIdex][j];
                }
            }
        }

        return sum;
    }
}
