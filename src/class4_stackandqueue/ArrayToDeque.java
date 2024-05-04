package class4_stackandqueue;

//数组实现循环双端队列
// 测试链接：https://leetcode.cn/problems/design-circular-deque/
public class ArrayToDeque {
    class MyCircularDeque {
        int queue[];
        int size, limit;
        int l, r; //l 指向队首元素，r指向队尾元素

        MyCircularDeque(int k) {
            queue = new int[k];
            limit = k;
            size = 0;
            l = r = 0;
        }

        public boolean insertFront(int value) {
            if (isFull()) {
                return false;
            } else {
                if (isEmpty()) { //队列空时l == r， 但可能在任意位置，让l，r回到位置0，
                    l = r = 0;
                    queue[0] = value;
                } else {
                    l = l == 0 ? (limit - 1) : (l - 1);
                    queue[l] = value;
                }
                size++;
                return true;
            }
        }

        public boolean insertLast(int value) {
            if (isFull()) {
                return false;
            } else {
                if (isEmpty()) { //队列空时l == r， 但可能在任意位置，让l，r回到位置0，
                    l = r = 0;
                    queue[0] = value;
                } else {
                    r = r == limit - 1 ? 0 : (r + 1);
                    queue[r] = value;
                }
                size++;
                return true;
            }
        }

        public boolean deleteFront() {
            if (isEmpty()) {
                return false;
            } else {
                l = l == (limit - 1) ? 0 : (l + 1);
                size--;
                return true;
            }
        }

        public boolean deleteLast() {
            if (isEmpty()) {
                return false;
            } else {
                r = r == 0 ? (limit - 1) : (r - 1);
                size--;
                return true;
            }
        }

        public int getFront() {
            if (isEmpty()) {
                return -1;
            } else {
                return queue[l];
            }
        }

        public int getRear() {
            if (isEmpty()) {
                return -1;
            } else {
                return queue[r];
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }
    }
}
