package class30_morris;

public class IsBST {
    public static boolean isBST(Node root) {
        if (root == null) {
            return true;
        }
        Node curr = root;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (curr != null) {
            mostRight = curr.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != curr) {
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null) {
                    mostRight.right = curr;
                    curr = curr.left;
                    continue;
                }
            }
            if (pre != null && pre >= curr.value) {
                ans = false; //这里不能马上return，因为过程中修改了树的某些节点指针，最终才会还原，因此要跑到最后
            }
            pre = curr.value;
            curr = curr.right;
        }
        return ans;
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

        System.out.println(isBST(head));
    }
}
