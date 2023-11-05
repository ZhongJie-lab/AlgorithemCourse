package class4_stackandqueue;

//链表实现栈和队列
public class LinkedListToQueueAndStack {
    /*
     * 队列 先进先出 从队尾(tail)进，从队头(head)出
     * @param <V>
     */
    static class MyQueue<V> {
        private Node<V> head;
        private Node<V> tail;
        private int size;

        public MyQueue() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void offer(V value) {
            Node<V> cur = new Node<> (value);
            if (tail == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                tail = cur;
            }
            size++;
        }

        public V poll() {
            V ans = null;
            if (head != null) {
                ans = head.value;
                head = head.next;
                size--;
            }
            //如果队列只有一个元素，取完元素以后，需要将tail置为null
            if (head == null) {
                tail = null;
            }
            return ans;
        }

        public V peek() {
            V ans = null;
            if (head != null) {
                ans = head.value;
            }
            return ans;
        }
    }

    /*
     * 栈 先进后出 head是最后加入的元素
     * @param <V>
     */
    static class  MyStack<V> {
        private Node<V> head;
        private int size;

        private MyStack() {
            head = null;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(V value) {
            Node<V> cur = new Node<>(value);
            if (head == null) {
                head = cur;
            } else {
                cur.next = head;
                head = cur;
            }
            size++;
        }

        public V pop() {
            V ans = null;
            if (head != null) {
                ans = head.value;
                head = head.next;
                size--;
            }
            return ans;
        }

        public V peek() {
            V ans = null;
            if (head != null) {
                ans = head.value;
            }
            return ans;
        }
    }

    static class Node<V> {
        public V value;
        public Node<V> next;

        public Node(V value) {
            this.value = value;
            this.next = null;
        }
    }
}


