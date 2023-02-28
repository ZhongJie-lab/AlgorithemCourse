package class11_12_binaryTree.dfs;


public class IsFullBT {
    //方法一：满二叉树定理：2 ^ h - 1 == n
    public static boolean isFullBT1(TreeNode root) {
        if (root == null) return true;

        Info1 rootInfo = process1(root);
        return (1 << rootInfo.height) - 1 == rootInfo.height;
    }

    private static Info1 process1(TreeNode root) {
        if (root == null) {
            return new Info1(0, 0);
        }

        Info1 linfo = process1(root.left);
        Info1 rinfo = process1(root.right);

        int h = Math.max(linfo.height, rinfo.height);
        int nodes = linfo.nodes + rinfo.nodes + 1;

        return new Info1(h, nodes);
    }

    //方法二：左满二叉树 + 右满二叉树 + 左右高度一样
    public static boolean isFullBT2(TreeNode root) {
        if (root == null) return true;

        return process2(root).isFull;
    }

    private static Info2 process2(TreeNode node) {
        if (node == null) {
            return new Info2(true, 0);
        }
        Info2 lInfo = process2(node.left);
        Info2 rInfo = process2(node.right);

        boolean isFull = lInfo.isFull && rInfo.isFull && lInfo.height == rInfo.height;
        int h = Math.max(lInfo.height, rInfo.height) + 1;

        return new Info2(isFull, h);
    }

    public static class Info1 {
        int height;
        int nodes;

        public Info1(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static class Info2 {
        boolean isFull;
        int height;

        public Info2(boolean isFull, int height) {
            this.isFull = isFull;
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
