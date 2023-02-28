package class10_linkedList.basic;

public class IntersectionNode {
    //https://leetcode.cn/problems/intersection-of-two-linked-lists/
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        return null;
    }

    public ListNode getIntersectionNodeII(ListNode headA, ListNode headB) {
        ListNode l1 = headA;
        ListNode l2 = headB;

        int n = 0;
        while (l1 != null) {
            n++;
            l1 = l1.next;
        }
        while (l2 != null) {
            n--;
            l2 = l2.next;
        }

        ListNode longNode = n >= 0 ? headA : headB;
        ListNode shortNode = longNode == headA ? headB : headA;

        n = Math.abs(n);
        while (n > 0) {
            longNode = longNode.next;
            n--;
        }

        while (longNode.val != shortNode.val) {
            longNode = longNode.next;
            shortNode = shortNode.next;
        }
        return longNode;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            this.val = v;
        }
    }
}
