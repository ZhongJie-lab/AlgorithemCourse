package class10_linkedList;

import java.util.Stack;

public class IsPalindromeList {
    //使用容器，空间复杂度O(n)
    public static boolean isPalindrome1(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null) {
            if (head.val != stack.pop().val)
                return false;
            head = head.next;
        }
        return true;
    }

    //使用容器，空间复杂度O(n/2)
    public static boolean isPaliendrome2(Node head) {
        Node right = head.next; //slow 奇数长度返回中点的下一个，偶数长度返回上中点的下一个(下中点)
        Node cur = head; //fast
        while(cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        Stack<Node> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        while (!stack.isEmpty()) {
            if (stack.pop().val != head.val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    //额外空间复杂度O(1)
    //后半段翻转以后和前半段比较
    public static boolean isPaliendrome3(Node head) {
        if (head == null || head.next == null) return true;

        Node node1 = head; //slow
        Node node2 = head; //fast

        //找到中点/上中点
        while(node2.next != null && node2.next.next != null) {
            node1 = node1.next;
            node2 = node2.next.next;
        }

        node2 = node1.next; //node2来到中点的下一个位置，等待翻转
        node1.next = null; //node1是中点或上中点，next指向空，打断链表
        Node node3 = null;

        //反转中点以后的链表 node1 - prev, node3 - next, node2 - curr
        while (node2 != null) {
            node3 = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = node3;
        }
        //此时，node1 是翻转后的头节点也即整个链表的最后一个结点，用node3记住最后的节点，为了后面恢复链表
        node2 = head;
        node3 = node1;
        boolean res = true;

        //2段链表，各自从头节点开始比较
        while (node1 != null && node2 != null) {
            if (node1.val != node2.val) {
                res = false;
                break;
            }
            node1 = node1.next;
            node2 = node2.next;
        }

        //恢复链表
        node1 = node3.next;
        node3.next = null; //翻转尾结点
        while (node1 != null) {
            node2 = node1.next; // node2 - next; node1 - head; node3 - prev
            node1.next = node3;
            node3 = node1;
            node1 = node2;
        }

        return res;
    }

    public static boolean isPalindrome33(Node head) {
        if (head == null || head.next == null) return true;

        //找到中点/上中点
        Node node1 = head;
        Node node2 = head;
        while (node2 != null && node2.next != null) {
            node1 = node1.next;
            node2 = node2.next.next;
        }

        //反转中点以后的链表
        //node1 是中点或上中点，node2是null
        node2 = node1.next;
        node1.next = null;
        Node node3 = null;
        while (node2 != null) {
            node3 = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = node3;
        }
        //2段链表，各自从头节点开始比较
        node3 = node1; //记住反转以后的头
        node2 = head;
        boolean res = true;

        while (node1!= null && node2 != null) {
            if (node1.val != node2.val) {
                res = false;
                break;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        //恢复链表
        node1 = node3.next;
        node3.next = null;
        while (node1 != null) {
            node2 = node1.next;
            node1.next = node3;
            node3 = node1;
            node1 = node2;
        }
        return res;
    }

    public static class Node {
        int val;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }
}
