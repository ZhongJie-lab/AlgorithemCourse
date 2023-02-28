package class10_linkedList.basic;

public class DeleteNode {
    public ListNode removeElement(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;
        while (curr.next != null){
            if (curr.next.val == val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    public ListNode removeElement2(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur.next != null) {
            if (cur.next.val == val) { //找到要删的节点，剔除该节点以后，不要往下跳，新指向的next还没判断，可能还是要删的节点
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;
        int len = getLen(head);

        for (int i = 0; i < len - n; i++) { //来到前一个节点 (len - n - 1) + 1 有个头节点0
            curr = curr.next;
        }
        curr.next = curr.next.next;
        return dummy.next;
    }

    private int getLen(ListNode cur) {
        int len = 0;
        while (cur != null)  {
            len++;
            cur = cur.next;
        }
        return len;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        int len = getLen(cur);
        for (int i = 0; i < len - n; i++) {
            cur = cur.next;
        }
        cur.next = cur.next.next;

        return dummy.next;
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
