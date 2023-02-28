package class27_KMP;

import java.util.ArrayList;
import java.util.Arrays;

/*
给定两棵二叉树的头节点head1和head2
想知道head1中是否有某个子树的结构和head2完全一样
 */
public class TreeEqual {
    //方法一 递归
    public static boolean containsTree1(Node big, Node small) {
        if (small == null) return true;
        if (big == null) return false;

        if (sameValueStructrue(big, small)) {
            return true;
        }

        return  containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    private static boolean sameValueStructrue(Node head1, Node head2) {
        if (head1 == null && head2 == null) {
            return true;
        }

        if (head1 != null && head2 == null) {
            return false;
        }

        if (head1 == null && head2 != null) {
            return false;
        }

        if (head1.val != head2.val) {
            return false;
        }

        return sameValueStructrue(head1.left, head2.left) && sameValueStructrue(head1.right, head2.right);
    }

    //方法二 KMP
    public static boolean containsTree2(Node big, Node small) {
        if (small == null) return true;
        if (big == null) return false;

        ArrayList<String> b = preSerial(big);
        ArrayList<String> s = preSerial(small);

        String[] str = new String[b.size()];
        for (int i = 0; i < b.size(); i++) {
            str[i] = b.get(i);
        }
        String[] match = new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            match[i] = s.get(i);
        }

        return getIndexOf(str, match) != -1;
    }

    private static int getIndexOf(String[] str, String[] match) {
        if (str.length < match.length) return -1;

        int x = 0;
        int y = 0;
        int[] next = getNextArray(match);
        while (x < str.length && y < match.length) {
            if (isEqual(str[x], match[y])) {
                x++;
                y++;
            } else if (next[y] != -1) {
                y = next[y];
            } else {
                x++;
            }
        }

        return y == match.length ? x - y : -1;
    }

    private static int[] getNextArray(String[] ms) {
        if (ms.length == 1) {
            return new int[] { -1 };
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;

        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (isEqual(ms[i - 1], ms[cn])) {
                next[i] = ++cn;
                i++;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i] = 0;
                i++;
            }
        }
        return next;
    }

    private static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) return true;

        if (s1 == null || s2 == null) return false;

        return s1.equals(s2);
    }

    private static ArrayList<String> preSerial(Node head) {
        ArrayList<String> res = new ArrayList<>();
        preSerialize(head, res);
        return res;
    }

    private static void preSerialize(Node head, ArrayList<String> res) {
        if (head == null) {
            res.add("null");
        } else {
            res.add(String.valueOf(head.val));
            preSerialize(head.left, res);
            preSerialize(head.right, res);
        }
    }


    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int v) {
            val = v;
        }
    }


    //for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return  null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 10000;

        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("end test");
    }
}
