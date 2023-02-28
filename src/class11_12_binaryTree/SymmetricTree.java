package class11_12_binaryTree;

//https://leetcode-cn.com/problems/symmetric-tree/
public class SymmetricTree {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
        //return isMirror(root, root);
    }

    public boolean isMirror(TreeNode p, TreeNode q) {
        if (p == null ^ q == null) return false;
        if (p == null && q == null) return true;

        return p.val == q.val && isMirror(p.left, q.right) && isMirror(p.right, q.left);
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
