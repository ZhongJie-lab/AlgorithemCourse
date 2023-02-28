package class10_linkedList;

public class SmallerEqualBigger {
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
