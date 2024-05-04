package class4_stackandqueue;

//数组实现栈和队列
public class ArrayToQueueAndStack {
    class My_Stack {
        private int size;
        private int[] arr;
        private int limit;
        private int index; //新进来的数放的位置

        public My_Stack(int n) {
            size = 0;
            arr = new int[n];
            limit = n;
            index = 0;

        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(int value) throws Exception {
            if (index == limit) {
                throw new Exception("stack is full");
            }
            arr[index++] = value;
            size++;
        }

        public int pop() throws Exception {
            if (size == 0) {
                throw new Exception("stack is empty");
            }
            int ans = arr[index - 1];
            size--;
            index--;
            return ans;
        }

        public int peek() throws Exception {
            if (size == 0) {
                throw new Exception("stack is empty");
            }
            return arr[index - 1];
        }
    }

    //循环数组实现队列
    class My_Queue {
        private int size;
        private int[] arr;
        private int limit;
        private int pollInd; //end 从哪里弹出
        private int pushInd; //start 从哪里加入

        public My_Queue(int limit) {
            arr = new int[limit];
            this.size = 0;
            this.limit = limit;
            pollInd = 0;
            pushInd = 0;
        }
        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(int value) throws Exception {
            if (size == limit) {
                throw new Exception("Queue is full!");
            }
            arr[pushInd] = value;
            pushInd = getNextInd(pushInd);
            size++;
        }

        public int pop() throws Exception {
            if (size == 0) {
                throw new Exception("Queue is empty!");
            }
            int ans = arr[pollInd];
            pollInd = getNextInd(pollInd);
            size--;
            return ans;
        }

        private int getNextInd(int ind) {
            return ind < limit - 1 ? ind + 1 : 0;
        }
    }

    //不实际删元素，通过size控制
    class Stack1 {
        int[] stack;
        int size;

        //同时在栈里的元素不超过n个
        public Stack1(int n) {
            stack = new int[n];
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(int num) {
            stack[size++] = num;
        }

        public int pop() {
            return stack[--size];
        }

        public int peek() {
            return stack[size - 1];
        }

        public int size() {
            return size;
        }
    }

    //加入元素的总操作次数是多少，一定要明确。笔试面试会有一个明确的数据量
    class Queue1 {
        public int[] queue;
        public int l, r;

        public Queue1(int n) {
            queue = new int[n];
            l = r = 0;
        }

        public boolean isEmpty() {
            return l == r;
        }

        public void offer(int num) {
            queue[r++] = num;
        }

        public int poll() {
            return queue[l++];
        }

        public int head() {
            return queue[l];
        }

        public int tail() {
            return queue[r - 1];
        }

        public int size() {
            return r - l;
        }

    }

    //测试链接： https://leetcode.cn/problems/design-circular-queue/
    class MyCircularQueue {
        private int[] arr;
        private int size;
        private int limit;
        private int l, r; //l 指向队首元素，r指向队尾元素的后一个位置

        //同时在队列里的数字不超过k个
        public MyCircularQueue(int k) {
            arr = new int[k];
            limit = k;
            l = r = 0;
            size = 0;
        }

        //从队尾加入新元素
        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            } else {
                arr[r] = value; //保证r不会越界
                r = r == limit - 1 ? 0 : (r + 1); //r来到最后一个元素的下一个位置
                size++;
                return true;
            }
        }

        public int deQueue() {
            if (isEmpty()) {
                return -1;
            } else {
                l = l == limit - 1 ? 0 : (l + 1);
                size--;
                return arr[l];
            }
        }

        //返回队列头部的数字，不弹出
        public int front() {
            if (isEmpty()) {
                return -1;
            } else {
                return arr[l];
            }
        }

        //返回队列尾部的数字，不弹出
        public int rear() {
            if (isEmpty()) {
                return -1;
            } else {
                int last = r == 0 ? limit - 1 : r - 1;
                return arr[last];
            }
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }
    }
}

