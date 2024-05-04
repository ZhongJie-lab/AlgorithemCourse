package class10_linkedList;

public class SmallerEqualBigger {
    // 给你一个链表的头节点 head 和一个特定值 x
    // 请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
    // 你应当 保留 两个分区中每个节点的初始相对位置
    // 测试链接 : https://leetcode.cn/problems/partition-list/
    public static Node listPartition2(Node head, int x) {
        Node leftHead = null, leftTail = null; // < x 的区域
        Node rightHead = null, rightTail = null; // >= x 的区域
        Node next = null;

        while (head != null) {
            next = head.next;
            head.next = null; //为什么？
            if (head.val < x) {
                if (leftHead == null) {
                    leftHead = head;
                } else {
                    leftTail.next = head;
                }
                leftTail = head; //尾结点来到下一个位置
            } else {
                if (rightHead == null) {
                    rightHead = head;
                } else {
                    rightTail.next = head;
                }
                rightTail = head; //尾结点来到下一个位置
            }
            head = next;
        }
        if (leftHead == null) {
            return rightHead;
        }

        leftTail.next = rightHead;
        return leftHead;
    }
    public static Node listPartition(Node head, int pivot) {
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node bH = null;
        Node bT = null;

        Node next = null;
        //分成3段
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sH.next = head;
                    sT = head;
                }
            } else if (head.val == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eH.next = head;
                    eT = head;
                }
            } else {
                if (bH == null) {
                    bH = head;
                    bT = head;
                } else {
                    bH.next = head;
                    bT = head;
                }
            }
            head = next;
        }

        //合并三个段
        //小于区的尾巴连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT; //谁去连大于区域的头
        }
        if (eT != null) {
            eT.next = bH; //连大于区域的头
        }

        return sH != null ? sH : (eH != null ? eH : bH);
    }


    public static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
            this.next = null;
        }
    }
}
