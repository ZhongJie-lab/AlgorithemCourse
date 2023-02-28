package class11_12_binaryTree;

//https://leetcode-cn.com/problems/same-tree/
public class SameTree {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null ^ q == null) return false;
        if (p == null && q == null) return true;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
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


