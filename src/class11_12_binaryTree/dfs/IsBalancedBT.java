package class11_12_binaryTree.dfs;

//https://leetcode.cn/problems/balanced-binary-tree
//平衡二叉树的定义：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过1
public class IsBalancedBT {
    public boolean isBalanced(TreeNode root) {
        return helper(root).isBalanced;
    }

    public TreeInfo helper(TreeNode root) {
        if (root == null) {
            return new TreeInfo(true, 0);
        }
        TreeInfo leftInfo = helper(root.left);
        TreeInfo rightInfo = helper(root.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = leftInfo.isBalanced && rightInfo.isBalanced
                && Math.abs(leftInfo.height - rightInfo.height) <= 1;
        return new TreeInfo(isBalanced, height);
    }

    public TreeInfo process(TreeNode root) {
        if (root == null) {
            return new TreeInfo(true, 0);
        }
        TreeInfo leftInfo = process(root.left);
        TreeInfo rightInfo = process(root.right);

        boolean isBal = true;
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBal = false;
        }
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new TreeInfo(isBal, height);
    }

    public static class TreeInfo {
        public boolean isBalanced;
        public int height;

        public TreeInfo(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
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
