package class11_12_binaryTree.bfs;

import java.util.LinkedList;
import java.util.Queue;

public class IsCompleteBT {
    public static boolean isCompleteBT(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode leftNode = null;
        TreeNode rightNode = null;
        boolean leaf = false; //是否遇到左右孩子不双全的结点

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            leftNode = curr.left;
            rightNode = curr.right;

            if ((leaf && (leftNode != null || rightNode != null)) || (leftNode == null && rightNode != null)) {
                return false;
            }

            if (leftNode != null) queue.add(leftNode);
            if (rightNode != null) queue.add(rightNode);

            leaf = leftNode == null || rightNode == null;
        }
        return true;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
    }
}
