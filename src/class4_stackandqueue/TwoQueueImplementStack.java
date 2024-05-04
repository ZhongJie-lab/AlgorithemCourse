package class4_stackandqueue;

import java.util.LinkedList;
import java.util.Queue;

//测试链接：https://leetcode.cn/problems/implement-stack-using-queues/
public class TwoQueueImplementStack {
    //2个queue实现
    public static class StackByQ<T> {
        public Queue<T> queue;
        public  Queue<T>  helper;

        public StackByQ() {
            queue = new LinkedList<>();
            helper = new LinkedList<>();
        }

        public void push(T value) {
            queue.offer(value);
        }

        public T pop() {
            while(queue.size() > 1) { //把queue里的元素移到helper，只留最后一个元素poll出来
                helper.offer(queue.poll());
            }
            T ans = queue.poll();
            //交换2个队列 queue和helper
            Queue<T> tmp = queue;
            queue = helper;
            helper = tmp;
            return ans;
        }

        public T peek() {
            while(queue.size() > 1) {
                helper.offer(queue.poll());
            }
            T ans = queue.peek();
            helper.offer(ans);
            Queue<T> tmp = queue;
            queue = helper;
            helper = tmp;
            return ans;
        }

        public boolean isEmpty(){
            return queue.isEmpty();
        }
    }

    //1个queue实现栈 O(n)
}
