package class11_12_binaryTree.bfs;

import java.util.LinkedList;
import java.util.Queue;

//https://leetcode.cn/problems/maximum-width-of-binary-tree/solution/by-mou-zi-ming-z-naex/
public class TreeMaxWidthII {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int ans = 0;
        //建立索引（从1开始）, 父节点i，左子 2i，右子2i + 1
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> indexes = new LinkedList<>();
        queue.add(root);
        indexes.add(1);
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean firstNode = false;
            int l = -1, r = -1;
            while (size != 0) {
                TreeNode node = queue.poll();
                int ind = indexes.poll();
                if (!firstNode) {
                    firstNode = true;
                    l = ind;
                }
                r = ind;

                if (node.left != null) {
                    queue.add(node.left);
                    indexes.add(2 * ind);
                }

                if (node.right != null) {
                    queue.add(node.right);
                    indexes.add(2 * ind + 1);
                }
                size--;
            }
            ans = Math.max(ans, r - l + 1);
        }

        return ans;
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
