package class4_stackandqueue;

import java.util.Stack;

// 测试链接 : https://leetcode.cn/problems/implement-queue-using-stacks/
public class TwoStackImplementQueue {
    //均摊下来是O(1)，每个数，进in栈一次出in栈一次，进out栈一次出out栈一次
    public static class QByStack<T> {
        //2个栈，一个栈只负责放数据，另一个栈只负责弹出数据
        //当用户要数据时，把in栈里的元素倒入out栈，则从out栈里取到的数据就是
        //倒数据原则：1. 一次性倒完，2. out栈空从才能倒，保证数据顺序不会乱

        //可以任何操作每次都做 pushToPull检查倒数据，也可以要拿数据时这么做
        public Stack<T> inStack;
        public Stack<T> outStack;
        public QByStack() {
            inStack = new Stack<T>();
            outStack = new Stack<T>();
        }

        public void add(T value) {
            inStack.push(value);
            inToOut();
        }

        public T poll() throws Exception {
            if (outStack.isEmpty() && inStack.isEmpty()) {
                throw new Exception("Queue is empty!");
            }
            inToOut();
            return outStack.pop();
        }

        public T peek() throws Exception {
            if (outStack.isEmpty() && inStack.isEmpty()) {
                throw new Exception("Queue is empty!");
            }
            inToOut();
            return outStack.peek();
        }

        //pop栈为空时，把数据从push栈倒入poll栈
        private void inToOut(){
            if(outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        QByStack test = new QByStack();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }


}
