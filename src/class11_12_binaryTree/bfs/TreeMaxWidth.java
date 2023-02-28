package class11_12_binaryTree.bfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TreeMaxWidth {
    //方法一：使用容器
    public static int treeMaxWidth(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        Map<TreeNode, Integer> levelMap = new HashMap<>(); //v - k在哪一层

        queue.add(root);
        levelMap.put(root, 1);
        int currLevel = 1; //记录当前来到的是第几层
        int currLevelNodes = 0; //记录当前层的节点数
        int max = 0;
        while(!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            int currNodeLevel = levelMap.get(curr);
            if (curr.left != null) {
                queue.add(curr.left);
                levelMap.put(curr.left, currNodeLevel + 1);
            }
            if (curr.right != null) {
                queue.add(curr.right);
                levelMap.put(curr.right, currNodeLevel + 1);
            }
            if(currLevel == currNodeLevel) { //当前层就是当前结点的层，那么当前层的节点数加一
                currLevelNodes++;
            } else { //当前已经来到了下一层，也即上一层结束了，可以计算结果；当前层加一，当前层节点数重置为1
                max = Math.max(max, currLevelNodes);
                currLevel++;
                currLevelNodes = 1;
            }
        }
        return max;
    }

    public static int treeMaxWidth11(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        Map<TreeNode, Integer> map = new HashMap<>(); //k - node; v - node所在层数
        map.put(root, 1);
        queue.add(root);

        int max = 0;
        int currLevelNodes = 0;
        int currLevel = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            Integer currNodeLevel = map.get(node);

            if (node.left  != null) {
                queue.add(node.left);
                map.put(node.left, currNodeLevel + 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                map.put(node.right, currNodeLevel + 1);
            }
            if (currLevel == currNodeLevel) {
                currLevelNodes++;
            } else { //已经来到下一次
                max = Math.max(max, currLevelNodes);
                currLevelNodes = 1;
                currLevel++;
            }
        }

        return max;
    }

    //方法二：有限变量，空间复杂度O(1)
    public static int treeMaxWidth2(TreeNode root) {
        if (root == null) return 0;
        int max = 0;

        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode currEnd = root; //在当前层的时候，就要注意计算下一层的右边界最右
        TreeNode nextEnd = null;
        int currLevelNodes = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left != null) {
                queue.add(curr.left);
                nextEnd = curr.left;
            }
            if (curr.right != null) {
                queue.add(curr.right);
                nextEnd = curr.right;
            }
            currLevelNodes++;
            if (curr == currEnd) { //判断层结束，已经来到当前层最后一个节点
                max = Math.max(max, currLevelNodes);
                currEnd = nextEnd;
//                nextEnd = null;
                currLevelNodes = 0;
            }
        }
        return max;
    }

    public static int treeMaxWidth22(TreeNode root) {
        if (root == null) return 0;
        int max = 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode curEnd = root;
        TreeNode nextEnd = null;
        int currLeveNodes = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left  != null) {
                queue.add(node.left);
                nextEnd = node.left;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextEnd = node.right;
            }
            currLeveNodes++;
            if (node == curEnd) { //当前层结束
                max = Math.max(max, currLeveNodes);
                curEnd = nextEnd;
                currLeveNodes = 0;
            }
        }
        return max;
    }

    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
}
