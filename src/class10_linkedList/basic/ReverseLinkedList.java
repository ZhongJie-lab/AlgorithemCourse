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
