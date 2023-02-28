package class10_linkedList;

import java.util.HashMap;
import java.util.Map;

//https://leetcode.cn/problems/copy-list-with-random-pointer/
public class CopyListWithRandom {
    //方法一：使用容器
    public static Node copyListWithRandom(Node head) {
        Map<Node, Node> map = new HashMap<>();
        Node curr = head;
        while (curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            map.get(curr).next = map.get(curr.next);
            map.get(curr).random = map.get(curr.random);
            curr = curr.next;
        }
        return map.get(head);
    }

    //方法二：
    public static Node copyListWithRandom2(Node head) {
        if (head == null) return null;
        Node curr = head;
        Node next = null;
        // 1 -> 2 -> 3 -> null
        // 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> null
        while (curr != null) {
            next = curr.next;
            curr.next = new Node(curr.val);
            curr.next.next = next;
            curr = next;
        }

        //依次设置1‘ 2’ 3‘ 的random指针
        curr = head;
        Node copyNode = null;
        while (curr != null) {
            next = curr.next.next;
            copyNode = curr.next;
            copyNode.random = curr.random != null ? curr.random.next : null;
            curr = next;
        }

        Node ans = head.next;
        curr = head;
        //依次设置1‘ 2’ 3‘ 的next指针， next方向上新老链表分离
        while (curr != null) {
            next = curr.next.next;
            copyNode = curr.next;
            curr.next = next;
            copyNode.next = next != null ? next.next : null;
            curr = next;
        }
        return ans;
    }

    public static Node copyListWithRandom22(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;

        //插入clone节点
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.val);
            cur.next.next = next;
            cur = next;
        }

        //设置random指针
        cur = head;
        Node copyNode = null;
        while (cur != null) {
            next = cur.next.next;
            copyNode = cur.next;
            copyNode.random = cur.random == null ? null : cur.random.next;
            cur = next;
        }
        //原链表和新链表分离
        cur = head;
        Node res = head.next;
        while (cur != null) {
            next = cur.next.next;
            copyNode = cur.next;
            cur.next = next;
            copyNode.next = next == null ? null : next.next;
            cur = next;
        }
        return res;
    }

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
