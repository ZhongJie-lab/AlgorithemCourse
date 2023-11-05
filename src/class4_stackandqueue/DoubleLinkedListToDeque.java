package class4_stackandqueue;

//链表实现双端队列
public class DoubleLinkedListToDeque {
    public static class Node<V> {
        public V value;
        public Node<V> last;
        public Node<V> next;

        public Node(V v) {
            this.value = v;
            this.last = null;
            this.next = null;
        }
    }

    /**
     * 双端队列 两端皆可进可出
     * 用双向链表实现
     * @param <V>
     */
    public static class MyDeque<V> {
        private Node<V> head;
        private Node<V> tail;
        private int size;

        public MyDeque() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return this.size;
        }

        public void pushHead(V value) {
            Node<V> cur = new Node<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
            size++;
        }

        public void pushTail(V value) {
            Node<V> cur = new Node<>(value);
            if (tail == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                cur.last = tail;
                tail = cur;
            }
            size++;
        }

        public V pollHead() {
            V ans = null;
            if (head != null) {
                ans = head.value;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = head.next;
                    head.last = null;
                }
                size--;
            }
            return ans;
        }

        public V pollTail() {
            V ans = null;
            if (tail != null) {
                ans = tail.value;
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    tail = tail.last;
                    tail.next = null;
                }
                size--;
            }
            return ans;
        }

        public V peekHead() {
            V ans = null;
            if (head != null) {
                ans = head.value;
            }
            return ans;
        }

        public V peekTail() {
            V ans = null;
            if (tail != null) {
                ans = tail.value;
            }
            return ans;
        }
    }
}
