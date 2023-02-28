package class10_linkedList.hard;

//判断2个链表是否相交，链表可能有环也可能无环，返回第一个相交点
public class FindFirstIntersectNode {
    //1. 两个都没有环，有可能相交
    //2. 一个有环，一个没有环，不可能相交
    //3. 两个都有环，有可能相交
    public static Node getIntersetctNdoe(Node head1, Node head2) {
        if (head1 == null || head2 == null) return null;
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        //两个都无环
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }

        //两个都有环
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;
    }

    private static Node noLoop(Node head1, Node head2) {
        Node curr1 = head1;
        Node curr2 = head2;
        int n = 0;
        //判断谁长谁短
        while (curr1 != null) {
            curr1 = curr1.next;
            n++;
        }

        while (curr2 != null) {
            curr2 = curr2.next;
            n--;
        }

        curr1 = n > 0 ? head1 : head2; //谁长谁是curr1
        curr2 = curr1 == head1 ? head2 : head1;

        n = Math.abs(n);
        while (n > 0) {
            curr1 = curr1.next;
            n--;
        }

        while (curr1 != curr2) {
            curr1 = curr1.next;
            curr2 = curr2.next;
        }

        return curr1;
    }

    private static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node curr1 = null;
        Node curr2 = null;

        if (loop1 == loop2) {
            curr1 = head1;
            curr2 = head2;
            int n = 0;
            while (curr1 != null) {
                curr1 = curr1.next;
                n++;
            }
            while (curr2 != null) {
                curr2 = curr2.next;
                n--;
            }
            curr1 = n > 0 ? head1 : head2;
            curr2 = curr1 == head1 ? head2 : head1;

            n = Math.abs(n);
            while (n > 0) {
                curr1 = curr1.next;
                n--;
            }

            while (curr1 != curr2){
                curr1 = curr1.next;
                curr2 = curr2.next;
            }
            return curr1;
        } else {
            curr1 = loop1.next;
            while (curr1 != loop1) {
                if (curr1 == loop2) {
                    return loop1;
                }
                curr1 = curr1.next;
            }
            return null;
        }
    }

    private static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        Node slow = head.next;
        Node fast = head.next.next;

        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        //slow fast相遇 说明有环
        //fast回到头，每次都走一步，再次相遇，就是入环处
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public static class Node {
        int val;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }
}
