package class10_linkedList;

// 给你两个 非空 的链表，表示两个非负的整数
// 它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字
// 请你将两个数相加，并以相同形式返回一个表示和的链表。
// 你可以假设除了数字 0 之外，这两个数都不会以 0 开头
// 测试链接：https://leetcode.cn/problems/add-two-numbers/
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

    public static ListNode addTwoNumber31(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        ListNode l1 = head1;
        ListNode l2 = head2;
        ListNode last = null;

        int carry = 0;
        int curVal = 0;
        //先都加到l1身上
        while (l1 != null && l2 != null) {
            curVal = l1.val + l2.val + carry;
            l1.val = curVal % 10;
            carry = curVal / 10;

            last = l1;
            l1 = l1.next;
            l2 = l2.next;
        }

        //如果l2 先结束，则处理carry对l1后续元素的影响
        //如果l1 先结束，则要把l2接到l1上，这种情况要把l1的最后一个节点拿到手里，并处理carry对l1后续元素的影响
        if (l2 != null) {
          last.next = l2; //接上l2
          l1 = last.next; //l1来到还没处理的节点
//            last = null;
        }

        //处理carry对l1的影响
        while (l1 != null) {
            curVal = l1.val + carry;
            l1.val = curVal % 10;
            carry = curVal / 10;
            last = l1; //还得抓住最后一个节点
            l1 = l1.next;
        }

        if (carry != 0) {
            last.next = new ListNode(carry); //此时carry一定为1
        }

        return head1;
    }

    //不计算链表长度，且用一个for循环实现
    public static ListNode addTwoNumbers4(ListNode h1, ListNode h2) {
        ListNode ans = null, cur = null;
        int carry = 0;
        for (int sum, val; h1 != null || h2 != null; h1 = h1 == null ? null : h1.next, h2 = h2 == null ? null : h2.next) {
            sum = (h1 == null ? 0 : h1.val) + (h2 == null ? 0 : h2.val) + carry;
            val = sum % 10;
            carry = sum / 10;

            if (ans == null) { //第一次
                ans = new ListNode(val);
                cur = ans;
            } else {
                cur.next = new ListNode(val);
                cur = cur.next;
            }
        }

        if (carry == 1) {
            cur.next = new ListNode(1);
        }
        return ans;
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
