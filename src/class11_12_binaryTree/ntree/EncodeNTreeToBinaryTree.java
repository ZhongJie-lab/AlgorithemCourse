package class11_12_binaryTree.ntree;


import java.util.ArrayList;
import java.util.List;

//https://leetcode.cn/problems/encode-n-ary-tree-to-binary-tree 付费题
public class EncodeNTreeToBinaryTree {
    //树的每个结点的孩子，往二叉树的结点的左树的右边界上挂
    public static TreeNode encode(Node root) {
        if (root == null) return null;
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    private static TreeNode en(List<Node> children) {
        TreeNode curr = null;
        TreeNode head = null;
        for(Node child : children) {
            TreeNode currTreeNode = new TreeNode(child.val);
            if (head == null) {
                head = currTreeNode;
            } else {
                curr.right = currTreeNode; //往右边界上挂
            }
            curr = currTreeNode; //curr来到的它的右孩子，curr往右下滑
            curr.left = en(child.children); //递归处理curr的左子树
        }
        return head;
    }

    //结点有左树右边界，则该结点在多叉树中有孩子，否则在多叉树中没有孩子
    public static Node decode(TreeNode root) {
        if (root == null) return null;

        return new Node(root.val, de(root.left));
    }

    private static List<Node> de(TreeNode head) {
        List<Node> children = new ArrayList<>();
        while (head != null) {
            Node node = new Node(head.val, de(head.left));
            children.add(node);
            head = head.right;
        }
        return children;
    }

    public static TreeNode encode2(Node root) {
        if (root == null) return null;
        TreeNode head = new TreeNode(root.val);
        head.left = en2(root.children);
        return head;
    }

    private static TreeNode en2(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        for (Node child : children) {
               TreeNode tNode =  new TreeNode(child.val);
               if (head == null) {
                   head = tNode;
               } else {
                   cur.right = tNode;
               }

               cur = tNode;
               cur.left = en2(child.children);
        }

        return head;
    }

    public static Node decode2(TreeNode root) {
        if (root == null) return null;

        return new Node(root.val, de2(root.left));
    }

    private static List<Node> de2(TreeNode head) {
        List<Node> children = new ArrayList<>();

        while (head != null) {
            Node node = new Node(head.val, de2(head.left));
            children.add(node);
            head = head.right;
        }

        return children;
    }

    public static class Node {
        int val;
        List<Node> children = null;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }
        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }
}
