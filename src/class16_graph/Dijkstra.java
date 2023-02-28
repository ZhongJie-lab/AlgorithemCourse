package class16_graph;

import class16_graph.graph.Edge;
import class16_graph.graph.Graph;
import class16_graph.graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//有向无负权重的图
/*
1）必须指定一个原点
2）生成一个原点到各个点的最小距离表
一开始只有一条记录，即原点到自己的距离为0，已确定，原点到其他点的距离都是正无穷大
3）从距离表中选出没确定过的最小记录记录，通过这个点发出的边，更新原点到各个点的最小距离表，不断重复这一步
4）原点到各个点的记录都被确定过，过程停止，最小距离表得到
 */
public class Dijkstra {
    public static HashMap<Node, Integer> dijkstra(Node from) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        HashSet<Node> selectedNodes = new HashSet<>(); //打过对号的点

        distanceMap.put(from, 0);

        //从distanceMap中选一个距离最小的点，但要略过已经确定过的点-selectedNodes
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            int distance = distanceMap.get(minNode); //原点到minNode的距离
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!selectedNodes.contains(toNode)) { //之前没有计算过
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }

        return distanceMap;
    }

    private static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance) {
                minDistance = distance;
                minNode = node;
            }
        }

        return minNode;
    }

    // Dijkstral算法优化
    // 利用加强堆，某个节点最短距离改变时动态调整
    public static HashMap<Node, Integer> dijkstral2(Graph graph) {
        return null; //TODO..
    }
}
