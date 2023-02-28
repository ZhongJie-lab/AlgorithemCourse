package class16_graph;

// 最小生成树：
// 图G的生成树是一棵包含G的所有顶点的树（所有点都联通），树上所有权值总和表示代价，那么在G的所有生成树中代价最小的生成树称为图G的最小生成树

import class16_graph.graph.Edge;
import class16_graph.graph.Graph;
import class16_graph.graph.Node;

import java.util.*;

//边的权值从小到大依次考察，并查集，如果当前的边不会形成环就要当前边，如果会形成环就不要当前边
//考察完所有边之后，最小生成树的集合也得到了
public class Kruskal {
    public static Set<Edge> kruskalMST(Graph graph) {
        PriorityQueue<Edge> heap = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        });

        for (Edge edge : graph.edges) {
            heap.add(edge);
        }

        Set<Edge> res = new HashSet<>();
        UnionFind uf = new UnionFind(graph.nodes);
        while (!heap.isEmpty()) {
            Edge edge = heap.poll();
            if (!uf.isSameSet(edge.from, edge.to)) { //不是一个集合，所以不会有环
                res.add(edge);
                uf.union(edge.from, edge.to);
            }
        }

        return res;
    }

    public static class UnionFind {
        private HashMap<Node, Node> fatherMap;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(HashMap<Integer, Node> nodes) {
            for (Node node : nodes.values()) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != fatherMap.get(node)) {
                path.add(node);
                node = fatherMap.get(node);
            }

            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(Node a, Node b) {
            return fatherMap.get(a) == fatherMap.get(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) return;

            Node f1 = findFather(a);
            Node f2 = findFather(b);

            if (f1 != f2) {
                int aSize = sizeMap.get(f1);
                int bSize = sizeMap.get(f2);

                if (aSize >= bSize) {
                    fatherMap.put(f2, f1);
                    sizeMap.put(f1, aSize + bSize);
                    sizeMap.remove(f2);
                } else {
                    fatherMap.put(f1, f2);
                    sizeMap.put(f2, aSize + bSize);
                    sizeMap.remove(f1);
                }
            }
        }
    }
}
