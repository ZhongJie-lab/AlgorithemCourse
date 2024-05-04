package class4_stackandqueue;

import java.util.Stack;

//最小栈： push pop top 并能在常数时间内检索到最小元素的栈
// 测试链接 : https://leetcode.cn/problems/min-stack/
public class MinStack {
    class MyMinStack{
        private Stack<Integer> stack = new Stack<>();
        private Stack<Integer> minStack = new Stack<>();

        public MyMinStack(Stack<Integer> stack, Stack<Integer> minStack) {
            this.stack = stack;
            this.minStack = minStack;
        }

        public void push(int num) {
            stack.push(num);
            if (!minStack.isEmpty()) {
                minStack.push(num);
            } else {
                minStack.push(Math.min(minStack.peek(), num));
            }
        }

        public int poll() throws Exception {
            if (stack.isEmpty()) {
                throw new Exception("Stack is empty!");
            }
            minStack.pop();
            return stack.pop();
        }

        public int top() throws Exception {
            if (stack.isEmpty()) {
                throw new Exception("Stack is empty!");
            }
            return stack.peek();
        }

        public int getMin() throws Exception {
            if (minStack.isEmpty()) {
                throw new Exception("Stack is empty!");
            }
            return minStack.peek();
        }
    }
}
