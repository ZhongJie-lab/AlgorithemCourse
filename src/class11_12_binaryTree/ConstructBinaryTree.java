package class11_12_binaryTree;

import java.util.HashMap;
import java.util.Map;

//https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal
public class ConstructBinaryTree {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        Map<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return helper2(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderMap);
//        return helper(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1);
    }

    public TreeNode helper(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2) {
        if (l1 > r1) return null;

        TreeNode head = new TreeNode(preorder[l1]);
        if (l1 == r1) return head;

        int rootIndx = l2; //在中序数组中找到根节点的位置
        while (inorder[rootIndx] != preorder[l1]) {
            rootIndx++;
        }

        head.left = helper(preorder, l1 + 1, l1 + rootIndx - l2, inorder, l2, rootIndx - 1);
        head.right = helper(preorder, l1 + rootIndx - l2 + 1, r1, inorder, rootIndx + 1, r2);

        return head;
    }

    public TreeNode helper2(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2,
                            Map<Integer, Integer> inorderMap) {
        if (l1 > r1) return  null;

        TreeNode head = new TreeNode(preorder[l1]);
        if (l1 == r1) return head;

        int rootIndx = inorderMap.get(preorder[l1]);
        head.left = helper2(preorder, l1 + 1, l1 + rootIndx - l2, inorder, l2, rootIndx - 1, inorderMap);
        head.right = helper2(preorder, l1 + rootIndx - l2 + 1, r1, inorder, rootIndx + 1, r2, inorderMap);

        return head;
    }

    public TreeNode buildTree11(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) return null;

        return process(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);

    }

    private TreeNode process(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2) {
        if (l1 > r1) return null;

        TreeNode head = new TreeNode(preorder[l1]);
        if (l1 == r1) {
            return head;
        }

        int rootInd = l2;
        while (inorder[rootInd] != preorder[l1]) {
            rootInd++;
        }

        head.left = process(preorder, l1 + 1, l1 + (rootInd - l2), inorder, l2, rootInd - 1);
        head.right = process(preorder, l1 + (rootInd - l2) + 1, r1, inorder, rootInd + 1, r2);
        return head;
    }

    private TreeNode process11(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2) {
        if (l1 > r1) return null;

        TreeNode head = new TreeNode(preorder[l1]);
        if (l1 == r1) {
            return head;
        }


        int rootInd = l2;
        while (inorder[rootInd] != preorder[l1]) {
            rootInd++;
        }
        head.left = process11(preorder, l1 + 1, l1 + (rootInd - l2), inorder, l2, rootInd - 1);
        head.right = process11(preorder, l1 + (rootInd - l2) + 1, r1, inorder, rootInd + 1, r2);

        return head;
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
