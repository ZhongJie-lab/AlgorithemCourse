package class11_12_binaryTree.dfs;

public class MaxSubBSTHead {
    public static TreeNode maxSubBSTHead(TreeNode root) {
        return process(root).maxSubBSTTreeHead;
    }

    private static Info process(TreeNode head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int min = head.val;
        int max = head.val;
        int maxSubBSTSize = 0;
        TreeNode maxSubBSTHead = null;

        if (leftInfo != null) {
            min = Math.min(leftInfo.min, min);
            max = Math.max(leftInfo.max, max);
            maxSubBSTHead = leftInfo.maxSubBSTTreeHead;
            maxSubBSTSize = leftInfo.maxSbuBSTSize;
        }

        if (rightInfo != null) {
            min = Math.min(rightInfo.min, min);
            max = Math.max(rightInfo.max, max);
            if (rightInfo.maxSbuBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTTreeHead;
                maxSubBSTSize = rightInfo.maxSbuBSTSize;
            }
        }
        //如果左子和右子都是二叉搜索树，那么maxSubBSTHead 和 maxSubBSTSize会被颠覆
        //判断是二叉搜索树，左子树的maxSubBSTTreeHead正好是自己的左孩子，且左子树的最大值小于自己；
        // 右子树的maxSubBSTTreeHead正好是自己的右孩子，且右子树的最小值大于自己
        boolean isLeftBST = leftInfo == null ? true : (leftInfo.maxSubBSTTreeHead == head.left && leftInfo.max < head.val);
        boolean isRightBST = rightInfo == null ? true : (rightInfo.maxSubBSTTreeHead == head.right && rightInfo.min > head.val);

        if (isLeftBST && isRightBST) {
            maxSubBSTHead = head;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSbuBSTSize) + (rightInfo == null ? 0 : rightInfo.maxSbuBSTSize) + 1;
        }
        return new Info(min, max, maxSubBSTHead, maxSubBSTSize);
    }

    public static class Info {
        int min;
        int max;
        TreeNode maxSubBSTTreeHead;
        int maxSbuBSTSize;

        public Info(int min, int max, TreeNode maxSubHead, int maxSubBSTSize) {
            this.min = min;
            this.max = max;
            this.maxSbuBSTSize = maxSubBSTSize;
            this.maxSubBSTTreeHead = maxSubHead;
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
