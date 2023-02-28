package class11_12_binaryTree.dfs;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LowestAncestor {
    //方法一 使用容器
    public static TreeNode lowestAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) return null;
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(root, null);
        fillParentMap(root, parentMap);

        Set<TreeNode> set = new HashSet<>();

        while (node1 != null) {
            set.add(node1);
            node1 = parentMap.get(node1);
        }
        while (!set.contains(node2)) {
            node2 = parentMap.get(node2);
        }
        return node2;
    }

    private static void fillParentMap(TreeNode root, Map<TreeNode, TreeNode> parentMap) {
        if (root.left != null) {
            parentMap.put(root.left, root);
            fillParentMap(root.left, parentMap);
        }
        if (root.right != null) {
            parentMap.put(root.right, root);
            fillParentMap(root.right, parentMap);
        }
    }

    //方法二 递归模板
    public static TreeNode lowestAncestor2(TreeNode root, TreeNode node1, TreeNode node2) {
        return process(root, node1, node2).ancestor;
    }

    public static Info process(TreeNode head, TreeNode node1, TreeNode node2) {
        if (head == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(head.left, node1, node2);
        Info rightInfo = process(head.right, node1, node2);

        boolean findNode1 = leftInfo.findNode1 || rightInfo.findNode1 || head == node1;
        boolean findNode2 = leftInfo.findNode2 || rightInfo.findNode2 || head == node2;

        TreeNode ancestor = null;
        if (leftInfo.ancestor != null) {
            ancestor = leftInfo.ancestor; //答案不带head
        } else if (rightInfo.ancestor != null) {
            ancestor = rightInfo.ancestor; //答案不带head
        } else { //答案带上head
            if (findNode1 && findNode2) {
                ancestor = head;
            }
        }
        return new Info(findNode1, findNode2, ancestor);
    }

    public static class Info{
        boolean findNode1;
        boolean findNode2;
        TreeNode ancestor;

        public Info(boolean find1, boolean find2, TreeNode ancestor) {
            findNode1 = find1;
            findNode2 = find2;
            this.ancestor = ancestor;
        }
    }

    public static TreeNode lowestAncestor22(TreeNode root, TreeNode node1, TreeNode node2) {
        return process22(root, node1, node2).ancestor;
    }

    private static Info22 process22(TreeNode node, TreeNode node1, TreeNode node2) {
        if (node == null) {
            return new Info22(false, false, null);
        }
        Info22 lInfo = process22(node.left, node1, node2);
        Info22 rInfo = process22(node.right, node1, node2);

        boolean find1 = false;
        boolean find2 = false;
        TreeNode ancestor = null;

        find1 = lInfo.findNode1 || rInfo.findNode1 || node == node1;
        find2 = lInfo.findNode2 || rInfo.findNode2 || node == node2;

        if (lInfo.ancestor != null) {
            ancestor = lInfo.ancestor;
        } else if (rInfo.ancestor != null) {
            ancestor = rInfo.ancestor;
        } else {
            ancestor = find1 && find2 ? node : null;
        }

        Info22 res = new Info22(find1, find2, ancestor);

        return res;
    }

    public static class Info22 {
        TreeNode ancestor;
        boolean findNode1;
        boolean findNode2;

        public Info22(boolean find1, boolean find2, TreeNode ancestor) {
            findNode1 = find1;
            findNode2 = find2;
            this.ancestor = ancestor;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
