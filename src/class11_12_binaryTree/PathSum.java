package class11_12_binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PathSum {
    //https://leetcode.cn/problems/path-sum-ii
    //给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> output = new ArrayList<>();
        if (root == null) return output;
        LinkedList<Integer> path = new LinkedList<>();

        process(root, path, 0, targetSum, output);
        return output;
    }

    /*
     * path - 当前路径上的结点集合，root是即将要判断的结点
     */
    public void process(TreeNode node, LinkedList<Integer> path, int preSum, int targetSum, List<List<Integer>> output) {
        //如果root是叶子结点
        if (node.left == null && node.right == null) {
            if (node.val + preSum == targetSum) {
                path.add(node.val);
                output.add(copy(path));
                //清理现场, 移除加进去的结点，继续探索其它路径
                path.removeLast();
            }
            return;
        }
        //root不是叶子结点，子节点不为空则继续往下传
        path.add(node.val);
        preSum += node.val;
        if (node.left != null) {
            process(node.left, path, preSum, targetSum, output);
        }
        if (node.right != null) {
            process(node.right, path, preSum, targetSum, output);
        }
        //清理现场
        path.removeLast();
    }

    public List<Integer> copy(List<Integer> path) {
        List<Integer> copyList = new LinkedList<>();
        for (int i = 0; i < path.size(); i++) {
            copyList.add(path.get(i));
        }
        return copyList;
    }

    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        if (root == null) return new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();

        process2(root, 0, targetSum, path, ans);

        return ans;
    }

    private void process2(TreeNode node, int preSum, int targetSum, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (node.left == null && node.right == null) {
            //收集答案
            if (preSum + node.val == targetSum) {
                path.add(node.val);
                ans.add(copy(path)); //引用传递，不能直接传path，后面remove element会改变path，所以拷贝一份
                path.removeLast();//清理现场
            }
            return;
        }

        if (node.left != null) {
            path.add(node.val);
            process2(node.left, preSum + node.val, targetSum, path, ans);
            path.removeLast();
        }
        if (node.right != null) {
            path.add(node.val);
            process2(node.right, preSum + node.val, targetSum, path, ans);
            path.removeLast();
        }
    }

    //https://leetcode-cn.com/problems/path-sum
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        return process(root, targetSum);
    }

    //判断以root为根节点的树，是否存在路径和是rest的路径
    public boolean process(TreeNode node, int rest) {
        //如果来到了叶子结点
        if (node.left == null && node.right == null) {
            return node.val == rest;
        }

        boolean leftHas = false, rightHas = false;
        //如果是非叶子结点
        if(node.left != null) {
            leftHas = process(node.left, rest - node.val);
        } else {
            leftHas = false;
        }
        if (node.right != null) {
            rightHas = process(node.right, rest - node.val);
        } else {
            rightHas = false;
        }
        return leftHas || rightHas;
    }

    public boolean process11(TreeNode node, int rest) {
        if (node == null) return false;
        if (node.val == rest) return true;

        boolean leftHas = false, rightHas = false;

        if (node.left != null) {
            leftHas = process11(node.left, rest - node.val);
        }
        if (node.right != null) {
            rightHas = process11(node.right, rest - node.val);
        }
        return leftHas || rightHas;
    }

    boolean hasPathSum = false;
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if (root == null) return  false;
        process(root, 0, targetSum);
        return hasPathSum;
    }

    public void process(TreeNode root, int preSum, int targetSum) {
        //root是叶子结点
        if (root.left == null && root.right == null) {
            if (preSum + root.val == targetSum) {
                hasPathSum = true;
            }
            return;
        }
        //root是非叶子结点，子节点不空则往下传
        preSum += root.val;
        if (root.left != null)
            process(root.left, preSum, targetSum);

        if (root.right != null)
            process(root.right, preSum, targetSum);
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
