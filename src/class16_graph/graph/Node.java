package class16_graph.graph;

import java.util.ArrayList;

public class Node {
    public int value;
    public int in; //入度
    public int out; //出度
    public ArrayList<Node> nexts; //从它出发，邻居有哪些
    public ArrayList<Edge> edges; //从它出发，边有哪些

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
