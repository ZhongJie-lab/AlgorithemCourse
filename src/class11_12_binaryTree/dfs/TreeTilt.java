package class11_12_binaryTree.dfs;

//https://leetcode.cn/problems/binary-tree-tilt/
public class TreeTilt {
    private static int ans = 0;
    public int findTilt(TreeNode root) {
        if (root == null) return 0;
        process(root);
        return ans;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }

        Info lInfo = process(node.left);
        Info rInfo = process(node.right);

        int sum = lInfo.sum + rInfo.sum + node.val;
        int tilt = Math.abs(lInfo.sum - rInfo.sum);

        ans += tilt;
        return new Info(sum, tilt);
    }

    public static class Info{
        int sum;
        int tilt;

        public Info(int nodes, int tilt) {
            this.sum = nodes;
            this.tilt = tilt;
        }
    }

    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }
}
