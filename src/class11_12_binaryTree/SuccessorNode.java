package class11_12_binaryTree;

//后继节点，中序遍历之下，找结点node的后面一个节点
//1. 有右子树，最左结点，就是它的后继
//2. 无右子树，它是它父亲节点的右子，就再往上找，还是父节点右子，就一直往上，直到该节点是父节点的左子，这个父节点就是它的后继
// 自身作为左子树，或作为右往上追溯到出现祖先结点作为再往上的祖先的左子树
public class SuccessorNode {
    public static TreeNode getSuccessorNode(TreeNode node) {
        if (node == null) return node;
        if (node.right != null) {
            return getMostLeftNode(node);
        } else { //没有右子树
            TreeNode parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    private static TreeNode getMostLeftNode(TreeNode node) {
        if (node == null) return node;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static class TreeNode{
        int val;
        TreeNode parent;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            val = val;
            parent = null;
            left = null;
            right = null;
        }
    }
}
