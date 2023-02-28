package class11_12_binaryTree.bfs;

import java.util.*;

//https://leetcode.cn/problems/binary-tree-level-order-traversal-ii
public class BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Deque<List<Integer>> output = new LinkedList<>();
        if (root == null) return new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>();
            int size = queue.size();
            while (size > 0) {
                TreeNode curr = queue.poll();
                curLevel.add(curr.val);
                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
                size--;
            }
            output.addFirst(curLevel);
        }
        return new ArrayList<>(output);
    }

    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        Deque<List<Integer>> ans = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int size = 0;
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> level = new ArrayList<>();
            while (size != 0) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                size--;
            }
            ans.addFirst(level);
        }
        return new ArrayList<>(ans);
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
