package class16_graph;

import class16_graph.graph.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    public static void bfs(Node start) {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();

        queue.add(start);
        set.add(start);

        while(!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.println(curr.value);
            for (Node node : curr.nexts) {
                if (!set.contains(node)) {
                    set.add(node);
                    queue.add(node);
                }
            }
        }
    }

    //判定二部图
    public static void bipartiteGraphs(Node start) {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();

        queue.add(start);
        set.add(start);


    }
}
