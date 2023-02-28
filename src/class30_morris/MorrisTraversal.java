package class30_morris;

public class MorrisTraversal {
    //递归 遍历 时间复杂度O(N) 空间复杂度 - 树的高度
    public static void  process(Node root) {
        if (root == null) {
            return;
        }
        // 1    -   先
        process(root.left);
        // 2    -   中
        process(root.right);
        // 3    -   后
    }

    //morris 遍历 时间复杂度O(N) 空间复杂度O(1)
    public static void morris(Node root) {
        if (root == null) {
            return;
        }

        Node curr = root;
        Node mostRight = null;

        while (curr != null) {
            mostRight = curr.left;
            if (mostRight != null) {
                //来到左子树最右节点有2种情况：节点右指针为null；节点的右指针指向curr（说明是第2次来到这里）
                while (mostRight.right != null && mostRight.right != curr) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = curr;
                    curr = curr.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }

            curr = curr.right;
        }
    }

    public static void morrisPre(Node root) {
        if (root == null) {
            return;
        }
        Node curr = root;
        Node mostR = null;

        while (curr != null) {
            mostR = curr.left;
            if (mostR != null) {
                while (mostR.right != null && mostR.right != curr) {
                    mostR = mostR.right;
                }

                if (mostR.right == null) { //这里表示：curr有左子树，并且还是第一次访问curr
                    mostR.right = curr;
                    System.out.print(curr.value + " ");
                    curr = curr.left;
                    continue;
                } else { //mostR.right != curr
                    mostR.right = null;
                }
            } else { //说明curr没有左子树
                System.out.print(curr.value + " ");
            }
            curr = curr.right;
        }
        System.out.println();
    }

    public static void morrisIn(Node root) {
        if (root == null) {
            return;
        }
        Node curr = root;
        Node mostR = null;
        while (curr != null) {
            mostR = curr.left;
            if (mostR != null) {
                while (mostR.right != null && mostR.right != curr) {
                    mostR = mostR.right;
                }

                if (mostR.right == null) {
                    mostR.right = curr;
                    curr = curr.left;
                    continue;
                } else {
                    mostR.right = null;
                }
            }
            System.out.print(curr.value + " ");
            curr = curr.right;
        }
        System.out.println();
    }

    //第二次来到节点时结算，逆序打印它左树的右边界，最后再单独逆序打印整棵树的右边界
    public static void morrisPost(Node root) {
        if (root == null) {
            return;
        }
        Node curr = root;
        Node mostRight = null;
        while (curr != null) {
            mostRight = curr.left;
            if (mostRight != null) { //有左子树
                while (mostRight.right != null && mostRight.right != curr) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = curr;
                    curr = curr.left;
                    continue;
                } else {
                    mostRight.right = null;
                    printRightEdge(curr.left);
                }
            }
            curr = curr.right;
        }
        printRightEdge(root);
        System.out.println();
    }

    private static void printRightEdge(Node head) {
        Node tail = reverse(head);
        Node curr = tail;
        while (curr != null) {
            System.out.print(curr.value + " ");
            curr = curr.right;
        }
        reverse(tail);
    }

    private static Node reverse(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }

    public static class Node {
        int value;
        Node left;
        Node right;

        public Node(int val) {
            this.value= val;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);

        morrisPre(head);
        morrisIn(head);
        morrisPost(head);
    }
}
