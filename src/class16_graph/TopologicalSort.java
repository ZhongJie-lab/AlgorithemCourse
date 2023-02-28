package class16_graph;

import class16_graph.graph.Graph;
import class16_graph.graph.Node;

import java.util.*;

/*
前提：有向无环图G = (V, E), V顶点的线性序列称作一个拓扑序列，该顶点满足：
    若在有向无环图G中，从顶点Vi 到Vj 有一条路径，则在序列中Vi必须在顶点Vj之前
拓扑排序 Topological Sort：
    将一个有向无环图中所有顶点在不违反先决条件的前提下排成线性序列的过程称为拓扑排序
拓扑排序的方法：
    任何有向无环图DAG，其顶点都可以排在一个拓扑序列里
    1）从图中选择任意入度为0的顶点且输出之
    2）从图中删掉此顶点及其所有的出边，将其(邻居节点)入度减少1
    3）回到第一步继续执行
 */
//BFS
public class TopologicalSort {
    public static List<Node> sortedTopology(Graph graph) {
        // key - , value 当前剩余入度
        HashMap<Node, Integer> inMap = new HashMap<>();

        //只有入度为0的点才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }

        List<Node> reslut = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node curr = zeroInQueue.poll();
            reslut.add(curr);
            for (Node node : curr.nexts) {
                inMap.put(node, inMap.get(node) - 1); //入度减少1
                if (inMap.get(node) == 0) {
                    zeroInQueue.add(node);
                }
            }
        }
        return reslut;
    }

}
