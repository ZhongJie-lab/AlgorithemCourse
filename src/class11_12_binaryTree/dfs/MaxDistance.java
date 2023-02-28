package class11_12_binaryTree.dfs;

/*
    给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
    返回整棵二叉树的最大距离
 */
public class MaxDistance {
    public static int maxDistance(TreeNode root) {
        if (root == null) return 0;

        return process(root).maxDis;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int h = Math.max(lInfo.height, rInfo.height) + 1;
        int maxDis = 0;

        int lD = lInfo.maxDis;
        int rD = rInfo.maxDis;
        int d = lInfo.height + rInfo.height + 1;
        maxDis = Math.max(d, Math.max(lD, rD));

        return new Info(h, maxDis);

    }

    public static class Info {
        int height;
        int maxDis;

        public Info(int h, int maxD) {
            this.height = h;
            this.maxDis = maxD;
        }
    }

    private static Info2 process2(TreeNode node) {
        if (node == null) {
            return new Info2(0, 0);
        }

        Info2 lInfo = process2(node.left);
        Info2 rInfo = process2(node.right);

        int maxD = 0;

        int lD = lInfo.maxD;
        int rD = rInfo.maxD;
        int d = lInfo.height + rInfo.height + 1; //经过头节点
        maxD = Math.max(d, Math.max(lD, rD)); //Math.max(lD, rD) 不经过头节点

        int h = Math.max(lInfo.height, rInfo.height) + 1;

        return new Info2(maxD, h);
    }

    public static class Info2{
        int maxD;
        int height;

        public Info2(int maxD, int h) {
            this.height = h;
            this.maxD = maxD;
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
