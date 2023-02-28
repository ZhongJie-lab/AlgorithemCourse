package class9_trietree;

public class TrieTree {
    private Node root;

    public TrieTree(Node root) {
        this.root = root;
    }

    public void insert(String word) {
        if (word == null) return;
        char[] chars = word.toCharArray();
        Node node = root;
        node.pass++;
        int ind = 0;
        for (int i = 0; i < chars.length; i++) { //从左往右遍历字符
            ind = chars[i] - 'a'; //由字符对应成走哪条路
            if (node.nexts[ind] == null) {
                node.nexts[ind] = new Node();
            }
            node = node.nexts[ind]; //跳到下一个结点
            node.pass++;
        }
        node.end++;
    }

    public void delete(String word) {
        //先判断word是否存在
        if (search(word) != 0) {
            if (word == null) return;
            char[] chars = word.toCharArray();
            Node node = root;
            node.pass--;
            int ind = 0;
            for (int i = 0; i < chars.length; i++) {
                ind = chars[i] - 'a';
                if (--node.nexts[ind].pass == 0) {
                    node.nexts[ind] = null; //直接断开，node后面挂的节点会被GC自动回收
                    return;
                }
                node = node.nexts[ind];
            }
            node.end--;
        }
    }

    public int search(String word) {
        if (word == null) return 0;
        char[] chars = word.toCharArray();
        int ind = 0;
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            ind = chars[i] - 'a';
            if (node.nexts[ind] == null) { //该字符没有对应的路了，说明单词不存在
                return 0;
            }
            node = node.nexts[ind];
        }
        return node.end;
    }

    public int prefixNum(String prefix) {
        if (prefix == null) return 0;
        char[] chars = prefix.toCharArray();
        int ind = 0;
        Node node = root;
        for (int i = 0; i < chars.length; i++) {
            ind = chars[i] - 'a';
            if (node.nexts[ind] == null) {
                return 0;
            }
            node = node.nexts[ind];
        }

        return node.pass;
    }
}

class Node {
    public int pass;
    public int end;
    public Node[] nexts;

    public Node() {
        pass = 0;
        end = 0;
        nexts = new Node[26]; //26叉树，代表一共有26个字母（假定都是小写）
    }
}