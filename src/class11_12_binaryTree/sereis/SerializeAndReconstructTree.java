package class11_12_binaryTree.sereis;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SerializeAndReconstructTree {

    /*
    * 二叉树可以通过先序，后序或者层序遍历的方式来序列化和反序列化
    * 但是，二叉树无法通过中序遍历实现序列化和反序列化
    * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位也可能一样
    *        __2
    *       /
    *      1
    * 和
    *       1__
    *          \
    *           2
    * 补足空位的中序遍历结果都是{null, 1, null, 2, null}
    * */

    //先序遍历的序列化
    public static Queue<String> preSerial(TreeNode head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(TreeNode head, Queue<String> list) {
        if (head == null) {
            list.add(null);
        } else{
            list.add(String.valueOf(head.val));
            pres(head.left, list);
            pres(head.right, list);
        }
    }

    //后序遍历的序列化
    public static Queue<String> postSerial(TreeNode head) {
        Queue<String> ans = new LinkedList<>();
        posts(head, ans);
        return ans;
    }

    private static void posts(TreeNode head, Queue<String> list) {
        if (head == null) {
            list.add(null);
        } else {
            posts(head.left, list);
            posts(head.right, list);
            list.add(String.valueOf(head.val));
        }
    }

    //先序遍历的反序列化
    public static TreeNode buildByPreQueue(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) return null;
        return preBuild(prelist);
    }

    private static TreeNode preBuild(Queue<String> prelist) {
        String value = prelist.poll();
        if (value == null) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = preBuild(prelist);
        node.right = preBuild(prelist);
        return node;
    }

    //后序遍历的反序列化
    public static TreeNode buildByPostQueue(Queue<String> postList) {
        if (postList == null || postList.size() == 0) return null;
        Stack<String> stack = new Stack<>();
        while (!postList.isEmpty()) {
            stack.push(postList.poll());
        }
        //左右中 -》 中右左
        return postBuild(stack);
    }

    private static TreeNode postBuild(Stack<String> postStack) {
        String val = postStack.pop();
        if (val == null) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(val));
        node.right = postBuild(postStack);
        node.left = postBuild(postStack);
        return node;
    }

    public Queue<String> levelSerial(TreeNode root) {
        Queue<String> ans = new LinkedList<>();
        if (root == null) {
            ans.add(null);
        } else {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            ans.add(String.valueOf(root.val));
            while (!queue.isEmpty()) {
                TreeNode curr = queue.poll();
                if (curr.left == null) {
                    ans.add(null);
                } else {
                    ans.add(String.valueOf(curr.left.val));
                    queue.add(curr.left);
                }

                if(curr.right == null) {
                    ans.add(null);
                } else {
                    ans.add(String.valueOf(curr.right.val));
                    queue.add(curr.right);
                }
            }
        }
        return ans;
    }

    public static TreeNode biuldByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) return null;
        TreeNode root = generateNode(levelList.poll());
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            curr.left = generateNode(levelList.poll());
            curr.right = generateNode(levelList.poll());
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
        return root;
    }

    private static TreeNode generateNode(String val){
        if (val == null) return null;
        return new TreeNode(Integer.valueOf(val));
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
