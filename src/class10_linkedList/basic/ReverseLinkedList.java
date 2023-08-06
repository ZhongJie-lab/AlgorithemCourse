package class10_linkedList.basic;

public class ReverseLinkedList {
    public static ListNode reverseLinkedList(ListNode head) {
        ListNode prev = null;
        ListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static DeListNode reverseDeLinkedList(DeListNode head) {
        DeListNode prev = null;
        DeListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = prev;
            head.last = next;
            prev = head;
            head = next;
        }
        return prev;
    }

    //https://leetcode.cn/problems/reverse-linked-list-ii/
    //给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。
    // 请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) return head;

        //头结点可能发生改变，用虚拟结点指向头
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;
        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }

        ListNode leftNode = prev.next;
        ListNode rightNode = prev;
        for (int i = 0; i < right - left + 1; i++) {
            rightNode = rightNode.next;
        }

        //此时，得到一个子链表，并切断子链表
        ListNode curNode = rightNode.next; //先记住右边结点的next
        prev.next = null;
        rightNode.next = null;

        //开始反转链表
        reverseLinkedList(leftNode);
        leftNode.next = curNode;
        prev.next = rightNode;

        return dummy.next;
    }

    //方法二
    public static ListNode reverseBetween2(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;

        //让pre来到left的前一个位置，且始终指向待反转区域的第一个结点的前一个
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }

        //cur始终指向待反转区域的第一个结点
        //next始终是cur的下一个结点
        ListNode cur = pre.next;
        ListNode next = null;
        for (int i = 0; i < right - left; i++) {
            next = cur.next;
            cur.next = next.next;
            next.next = pre.next;
            pre.next = next;
        }
        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
            next = null;
        }
    }

    public static class DeListNode {
        int val;
        DeListNode last;
        DeListNode next;

        DeListNode() {
            this.val = val;
            this.last = null;
            this.next = null;
        }
    }
}
