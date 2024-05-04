package class4_stackandqueue;

import java.util.LinkedList;
import java.util.Queue;

// 测试链接 : https://leetcode.cn/problems/implement-stack-using-queues/
public class OneQueueImplementStack {
    //queue：尾进头出，每次进新元素，将前面已存在元素往后放 O(n^2)
    //和2个queue实现 栈的原理一样，O(n^2)
    class  MyStack {
        Queue<Integer> queue;

        public MyStack() {
            queue = new LinkedList<>();
        }

        public void push(int val) {
            int n = queue.size();
            queue.offer(val);
            for (int i = 0; i < n; i++) {
                queue.offer(queue.poll());
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
}
