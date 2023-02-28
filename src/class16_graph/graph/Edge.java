package class16_graph.graph;

public class Edge {
    public Node from;
    public Node to;
    public int weight;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
