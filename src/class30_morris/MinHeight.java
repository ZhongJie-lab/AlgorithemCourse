package class30_morris;

//二叉树的最小深度
public class MinHeight {
    //递归
    public static int minHeight1(Node head) {
        if (head == null) return 0;
        return process(head);
    }

    private static int process(Node node) {
        if (node.left == null && node.right == null) {
            return 1;
        }

        int h1 = Integer.MAX_VALUE, h2 = Integer.MAX_VALUE;
        if (node.left != null) {
            h1 = process(node.left);
        }
        if (node.right != null) {
            h2 = process(node.right);
        }
        return 1 + Math.min(h1, h2);
    }

    //morris
    // 1. 结点的level - X是上一个结点深度是level Y是当前
    // 1.1 - Y是X的左孩子，Y的深度是 level + 1
    // 1.2 - Y是X的右孩子，
    // 1.2.1 - Y左树的最右结点不是X，Y的深度是 level + 1
    // 1.2.2 - Y左树的最右结点是X，Y的深度是 level - (左树的右边界长度)
    // 2. 判断叶节点
    // 2.1 第二次访问结点时判断，返回来看是不是叶节点，同时结算叶节点的深度，
    // 2.2 最后在单独检查整棵树的最右节点是不是叶结点
    public static int minHeight2(Node head) {
        if (head == null) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        Node curr = head;
        Node mostRight = null;
        int currLevel = 0; //curr的深度
        while (curr != null) {
            mostRight = curr.left;
            if (mostRight != null) {
                int rightEdgeSize = 1; //左树右边界长度
                while (mostRight.right != null && mostRight.right != curr) {
                    rightEdgeSize++;
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { //第一次到达
                    currLevel++;
                    mostRight.right = curr;
                    curr = curr.left;
                    continue;
                } else { //第二次到达curr (mostRight.right == curr)
                    //判断mostRight是不是叶结点
                    if (mostRight.left == null) {
                        min = Math.min(min, currLevel);
                    }
                    currLevel -= rightEdgeSize;
                    mostRight.right = null;
                }
            } else { //只会到达一次
                currLevel++;
            }
            curr = curr.right;
        }

        //整棵树的最右结点，如果是叶节点，计算深度
        curr = head;
        int lastRHeight = 1;
        while (curr.right != null) {
            lastRHeight++;
            curr = curr.right;
        }
        if (curr.left == null && curr.right == null) {
            min = Math.min(min, lastRHeight);
        }
        return min;
    }

    public static class Node {
        int value;
        Node left;
        Node right;

        public Node(int val) {
            this.value= val;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int treeLevel = 7;
        int nodeMaxVal = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxVal);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");
    }
}
