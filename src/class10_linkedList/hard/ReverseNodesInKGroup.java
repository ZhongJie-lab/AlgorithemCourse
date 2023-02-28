package class10_linkedList.hard;

//https://leetcode.com/problems/reverse-nodes-in-k-group/
public class ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getKGroupEnd(start, k);
        if (end == null) {
            return head;
        }
        //第一组凑齐了
        head = end; //将head赋值为end，为新链表的头
        reverse(start, end);

        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

    private ListNode getKGroupEnd(ListNode start, int k) {
        while(--k > 0 && start != null) {
            start = start.next;
        }
        return start;
    }

    private void reverse(ListNode start, ListNode end) {
        end = end.next;
        ListNode curr = start;
        ListNode prev = null;
        ListNode next = null;

        while (curr != end) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        start.next = end;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getGroupEnd(start, k);

        if (end == null) {
            return head;
        }
        head = end; //作为新链表头
        reverseGrp(start, end);

        ListNode lastEnd = start;
        while (lastEnd != null) {
            start = lastEnd.next;
            end = getGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverseGrp(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

    private void reverseGrp(ListNode start, ListNode end) {
        end = end.next;
        ListNode pre = null;
        ListNode cur = start;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    private ListNode getGroupEnd(ListNode node, int k) {
        while (--k > 0 && node != null) {
            node = node.next;
        }
        return node;
    }
}




