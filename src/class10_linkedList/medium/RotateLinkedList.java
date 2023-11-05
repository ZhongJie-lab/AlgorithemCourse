package class10_linkedList.medium;

//给你一个链表的头节点 head, 旋转链表，将链表每个节点向右移动k个位置
//https://leetcode.cn/problems/rotate-list/
public class RotateLinkedList {
    //方法一：闭合成环
    public static ListNode rotateRight(ListNode head, int k) {
        if (k < 1 || head == null || head.next == null) {
            return head;
        }

        int len = 1;
        ListNode node = head;
        while (node.next != null) {
            len++;
            node = node.next;
        } //来到最后一个节点

        if (k % len == 0) {
            return head;
        }

        node.next = head; //首位相连

        int pos = len - k % len; //最后一个结点的位置
        while (pos-- > 0) {
            node = node.next;
        }

        ListNode result = node.next;
        node.next = null;

        return result;
    }

    //方法二：快慢指针
    public static ListNode rotateRight2(ListNode head, int k) {
        if (k < 1 || head == null || head.next == null) {
            return head;
        }

        int len = 1;
        ListNode node = head;
        while (node.next != null) {
            len++;
            node = node.next;
        } //来到最后一个节点

        if (k % len == 0) {
            return head;
        }

        k = k % len;

        ListNode slow = head;
        ListNode fast = head;

        //快指针先移动k步
        while (k-- > 0) {
            fast = fast.next;
        }

        //快慢指针一起
        while (fast.next != null) { //让快指针停留在链表尾，此时慢指针听了在新链表的尾部
            slow = slow.next;
            fast = fast.next;
        }

        fast.next = head;
        head = slow.next; //新表头

        slow.next = null;
        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }
}
