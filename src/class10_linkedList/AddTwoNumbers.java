package class10_linkedList;

public class AddTwoNumbers {
    public static ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        int len1 = getLen(head1);
        int len2 = getLen(head2);

        ListNode sNode = len1 <= len2 ? head1 : head2;
        ListNode lNode = sNode == head1 ? head2 : head1;

        ListNode currS = sNode;
        System.out.println(sNode.val);
        ListNode currL = lNode;
        System.out.println(lNode.val);
        ListNode last = null;
        int carry = 0;
        int curNum = 0;
        while (currS != null) {
            curNum = currS.val + currL.val + carry;
            carry = curNum / 10;
            currL.val = curNum%10;
            currS = currS.next;
            last = currL;
            currL = currL.next;

        }
        while (currL != null) {
            curNum = currL.val + carry;
            carry = curNum / 10;
            currL.val = curNum % 10;
            last = currL;
            currL = currL.next;
        }
        if (carry != 0) {
            last.next = new ListNode(carry);
        }
        return lNode;
    }

    private static int getLen(ListNode head){
        int len = 0;
        while (head != null) {
            head = head.next;
            len++;
        }
        return len;
    }

    public static ListNode addTwoNumbers2(ListNode head1, ListNode head2) {
        int len1 = getLen(head1);
        int len2 = getLen(head2);

        ListNode shortL = len1 <= len2 ? head1 : head2;
        ListNode longL = shortL == head1 ? head2 : head1;
        ListNode llL = longL;
        ListNode last = null;
        int carry = 0;

        while (shortL != null) {
            int cur = shortL.val + llL.val + carry;
            carry = cur / 10;
            llL.val = cur % 10;

            shortL = shortL.next;
            last = llL;
            llL = llL.next;
        }

        while (llL != null) {
            int cur = llL.val + carry;
            llL.val = cur % 10;
            carry = cur / 10;
            last = llL;
            llL = llL.next;
        }

        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return longL;
    }

    //不计算链表长度
    public static ListNode addTwoNumbers3(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        ListNode l1 = head1;
        ListNode l2 = head2;
        ListNode last = null;
        int cur = 0;
        int carry = 0;
        while (l1 != null && l2 != null) {
            cur = l1.val + l2.val + carry;
            l1.val = cur % 10;
            carry = cur / 10;
            last = l1;
            l1 = l1.next;
            l2 = l2.next;
        }

        if (l2 != null) {
            last.next = l2;
            l1 = last.next;
            last = null;
        }

        while (l1 != null) {
            cur = l1.val + carry;
            l1.val = cur % 10;
            carry = cur / 10;
            last = l1;
            l1 = l1.next;
        }
        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return head1;
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(5);

        ListNode head2 = new ListNode(5);

        ListNode res = addTwoNumbers3(head1, head2);

        while (res!= null) {
            System.out.print(res.val + " ");
            res = res.next;
        }
    }
}
