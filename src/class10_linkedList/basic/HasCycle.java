package class10_linkedList.basic;

public class HasCycle {
        public boolean hasCycle(ListNode head) {
                if (head == null || head.next == null) {
                        return false;
                }
                ListNode slow = head;
                ListNode fast = head.next;

                while (slow != fast) {
                        if (fast == null || fast.next == null) {
                                return false;
                        }
                        slow = slow.next;
                        fast = fast.next.next;
                }
                return true;
        }

        public static class ListNode {
                int val;
                ListNode next;

                public ListNode(int val) {
                        this.val = val;
                }
        }
}
