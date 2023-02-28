package class10_linkedList.basic;

public class LinkedListMid {
    //输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static ListNode midOrUpMidNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return head;

        ListNode slow = head; //head.next
        ListNode fast = head; //head.next.next

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static ListNode midOrDownMidNode(ListNode head) {
        if (head == null || head.next == null) return head;

        //让slow和fast第一步相同，都跨一步，下面再slow，fast，这样就转换为第一种情况
        ListNode slow = head.next;
        ListNode fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点的前一个，偶数长度返回上中点的前一个
    public static ListNode midOrUpMidPreNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return null;

        ListNode slow = head; //因为slow停下来的地方是最终的结果，要求前一个，就让slow慢一步启动
        ListNode fast = head.next.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点的前一个，偶数长度返回下中点的前一个
    public static ListNode midOrDownMidPreNode(ListNode head) {
        if (head == null || head.next == null) return null;
        if (head.next.next == null) return head;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

}
