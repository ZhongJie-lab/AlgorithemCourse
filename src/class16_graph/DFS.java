package class16_graph;

import class16_graph.graph.Graph;
import class16_graph.graph.Node;

import java.util.HashSet;
import java.util.Stack;

public class DFS {
    //迭代的方式进行深度优先遍历 - 一条路没走完就走到死，走完了就依次往上看哪条路还没走完
    public static void dfs(Node start) {
        Stack<Node> stack = new Stack<>(); //栈存放 的是目前的整条路径
        HashSet<Node> set = new HashSet<>(); //已经访问过的节点不再访问，禁止出现环路

        stack.push(start);
        set.add(start);
        System.out.println(start.value); //入栈的时候遍历它

        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            for (Node node : curr.nexts) {
                if (!set.contains(node)) {
                    stack.push(curr); //cur压回去，邻居也压回去
                    stack.push(node);
                    set.add(node);
                    System.out.println(node.value); //入栈的时候打印
                    break; //遇到一个邻居就别遍历了跳出循环，此时栈是目前的整条路径，体现了深度优先
                }
            }
        }
    }

    public static void dfs2(Node start) {
        HashSet<Node> visited = new HashSet<>();

        System.out.println(start);
        visited.add(start);

        for (Node node : start.nexts) {
            if (!visited.contains(node)) {
                dfs2(node);
            }
        }
    }
}
