package class11_12_binaryTree.dfs;

public class IsCompleteBT {
    public static boolean isCompleteBT(TreeNode root) {
        if (root == null) return true;
        return process(root).isComplete;
    }

    public static Info process(TreeNode head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int h = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isComplete = false;

        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isComplete = true;
        } else if (leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isComplete = true;
        } else if (leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height) {
            isComplete = true;
        }

        return new Info(isFull, isComplete, h);
    }

    public static class Info {
        boolean isFull;
        boolean isComplete;
        int height;

        public Info(boolean isFull, boolean isComplete, int height) {
            this.isFull = isFull;
            this.isComplete = isComplete;
            this.height = height;
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
