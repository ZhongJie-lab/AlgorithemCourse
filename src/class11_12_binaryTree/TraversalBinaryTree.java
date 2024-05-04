package class11_12_binaryTree;

import java.util.*;

public class TraversalBinaryTree {

    //深度优先遍历
    //先序遍历 递归 https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
    List<Integer> ans = new ArrayList<>();

    public List<Integer> preorderTraversal(TreeNode root) {
        if(root == null) return null;
        ans.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
        return ans;
    }

    //先序遍历 非递归
    /**
     * 把根结点推入栈中
     * 从栈顶弹出一个结点，访问它，然后，该结点的右孩子入栈，再左孩子入栈
     * 直到栈空
     */
    public List<Integer> preorderTraversalIII(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) return output;
        stack.push(root);

        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            output.add(curr.val);
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
        return output;
    }

    /**
     * 思想：
     * 遇到一个结点，就访问该结点，并且把此结点的非空右结点推入栈中，然后下降去遍历它的左子树
     * 遍历完左子树后，从栈顶弹出一个结点，并按照它的右链指示的地址再去遍历该结点的右子树结构
     */
    public List<Integer> preorderTraversalII(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        stack.push(null); //栈底监视哨
        while (curr != null) { //或者!stack.isEmpty()
            output.add(curr.val); //访问当前节点
            if(curr.right != null) { //右孩子入栈
                stack.push(curr.right);
            }
            if (curr.left != null) {
                curr = curr.left; //左路下降
            } else {               //左子树访问完毕，转向访问右子树
                curr = stack.pop(); //栈顶元素退栈
            }
        }
        return output;
    }

    //后序遍历 递归 https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return ans;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        ans.add(root.val);
        return ans;
    }

    //后序遍历 非递归
    /**
     * 可以借鉴先序遍历
     * 如果得到中右左的排列，通过另一个栈或双端队列，反过来取值，得到左右中的排列
     */
    public List<Integer> postorderTraversalII(TreeNode root) {
        Deque<Integer> output = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) return new ArrayList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            output.addFirst(curr.val);
            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }
        return new ArrayList<>(output);
    }


    //中序遍历 递归 https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
    public List<Integer> inOrderTraversal(TreeNode root) {
        if (root == null) return ans;
        inOrderTraversal(root.left);
        ans.add(root.val);
        inOrderTraversal(root.right);
        return ans;
    }

    //中序遍历 非递归
    /**
     * 思想：
     * 遇到一个结点
     *      把它推入栈中
     *      遍历其左子树
     * 遍历完左子树
     *      从栈顶弹出该结点并访问之
     *      按照其右链地址遍历该结点的右子树
     */
    public List<Integer> inorderTraversalII(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                output.add(curr.val);
                curr = curr.right;
            }
        }
        return output;
    }

    public List<Integer> inorderTraversalIII(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                ans.add(curr.val);
                curr = curr.right;
            }
        }
        return ans;
    }

    //宽度优先遍历
    public List<Integer> traversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode curr = queue.poll();
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
            ans.add(curr.val);
        }
        return ans;
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
