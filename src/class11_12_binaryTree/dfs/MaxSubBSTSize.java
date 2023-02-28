package class11_12_binaryTree.dfs;

public class MaxSubBSTSize {
    public static int maxSubBSTSize(TreeNode root) {
        if (root == null) return 0;

        return process(root).maxSubBTBSTSize;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }

        int min = node.val;
        int max = node.val;
        int size = 1;
        int maxSubBTBSTSzie = 0;

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        if (lInfo != null) {
            max = Math.max(lInfo.max, max);
            min = Math.min(lInfo.min, min);
            size += lInfo.size;
        }
        if (rInfo != null) {
            max = Math.max(rInfo.max, max);
            min = Math.min(rInfo.min, min);
            size += rInfo.size;
        }

        int num1 = lInfo == null ? 0 : lInfo.maxSubBTBSTSize;
        int num2 = rInfo == null ? 0 : rInfo.maxSubBTBSTSize;

        //带上当前结点，满足 左是BST 右是BST 左的max < node.val 右的min > node.val，则可以得到结果
        int num3 = 0;
        //判断子树是二叉搜索树，本身的size正好等于最大搜索子树的size
        boolean isLBST = lInfo == null ? true : lInfo.size == lInfo.maxSubBTBSTSize;
        boolean isRBST = rInfo == null ? true : rInfo.size == rInfo.maxSubBTBSTSize;
        if (isLBST && isRBST) {
            boolean lmaxLessX = lInfo == null ? true : (lInfo.max < node.val);
            boolean rminMoreX = rInfo == null ? true : (rInfo.min > node.val);
            if (lmaxLessX && rminMoreX) {
                num3 = (lInfo == null ? 0 : lInfo.size) + (rInfo == null ? 0 : rInfo.size) + 1;
            }
        }
        maxSubBTBSTSzie = Math.max(Math.max(num1, num2), num3); //不带当前节点的结果，Math.max(num1, num2)

        return new Info(min, max, size, maxSubBTBSTSzie);
    }

    public static class Info {
        int min;
        int max;
        int size;
        int maxSubBTBSTSize;

        public Info(int min, int max, int size, int maxSubBTBSTSize) {
            this.min = min;
            this.max = max;
            this.size = size;
            this.maxSubBTBSTSize = maxSubBTBSTSize;
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
