package class15_unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code01_MyUnionFind {

    public static class Node<V> {
        V value;

        public Node(V v) {
            value = v;
        }
    }
    public static class UnionFind<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> curr = new Node(value);
                nodes.put(value, curr);
                parents.put(curr, curr);
                sizeMap.put(curr, 1);
            }
        }

        public Node<V> findParent(Node<V> curr) {
            Stack<Node<V>> path = new Stack<>();
            while ((curr != parents.get(curr))) {
                path.push(curr); //用栈记录节点
                curr = parents.get(curr); //我就来到我的父节点
            }
            //优化 ，扁平化
            while (!path.isEmpty()) {
                parents.put(path.pop(), curr);
            }
            return curr;
        }

        public boolean isSameSet(V a, V b) {
            return findParent(nodes.get(a)) == findParent(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> aHead = findParent(nodes.get(a));
            Node<V> bHead = findParent(nodes.get(b));
            if (aHead != bHead) { //如果不是同一个集合
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);

                Node<V> small = aSize <= bSize ? aHead : bHead;
                Node<V> bigger = small == aHead ? bHead : aHead;

                parents.put(small, bigger); // 优化，小挂大， 扁平
                sizeMap.put(bigger, aSize + bSize);

                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

}
