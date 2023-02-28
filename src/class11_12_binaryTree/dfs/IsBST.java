package class11_12_binaryTree.dfs;

//https://leetcode-cn.com/problems/validate-binary-search-tree/

/**
 * 搜索二叉树的定义：
 * 或者是一棵空树；
 * 或者是具有下列性质的二叉树：
 *  对于任何一个结点，设其值为K
 *  该结点的左子树的任意一个结点的值都小于K
 *  该结点的右子树的任意一个结点的值都大于K
 *  而且它的左右子树也分别是BST
 */
public class IsBST {
    public boolean isValidBST(TreeNode root) {
//        return isBST(root, null, null);
        return isBSTII(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isBST (TreeNode node, Integer lower, Integer upper) {
        if (node == null) return true;
        if ((lower != null && node.val <= lower)
                || (upper != null && node.val >= upper)) return false;
        return isBST(node.left, lower, node.val) && isBST(node.right, node.val, upper);
    }

    public boolean isBSTII (TreeNode node, long lower, long upper) {
        if (node == null) return true;
        if (node.val <= lower || node.val >= upper) return false;
        return isBSTII(node.left, lower, node.val) && isBSTII(node.right, node.val, upper);
    }

    public boolean isValidBST2(TreeNode root) {
        return process(root).isBST;
    }

    public Info process(TreeNode root) {
        if (root == null) return null;

        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        boolean isBST = true;
        int max = root.val;
        int min = root.val;

        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }

        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }

        if(leftInfo != null && leftInfo.max >= root.val) {
            isBST = false;
        }
        if(rightInfo != null && rightInfo.min <= root.val) {
            isBST = false;
        }
        return new Info(isBST, max, min);
    }

    public static class Info{
        boolean isBST;
        int max;
        int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
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
