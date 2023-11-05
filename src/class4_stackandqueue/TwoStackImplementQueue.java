package class4_stackandqueue;

import java.util.Stack;

public class TwoStackImplementQueue {
    //O(1)
    public static class QStack<T> {
        //2个栈，一个栈只负责放数据，另一个栈只负责弹出数据
        //当用户要数据时，把push栈里的元素倒入pop栈，则从pop栈里取到的数据就是
        //倒数据原则：1. 一次性倒完，2. pull栈空从才能倒，保证数据顺序不会乱

        //可以任何操作每次都做 pushToPull检查倒数据，也可以要拿数据时这么做
        public Stack<T> stackPush;
        public Stack<T> stackPop;
        public QStack() {
            stackPush = new Stack<T>();
            stackPop = new Stack<T>();
        }

        public void add(T value) {
            stackPush.push(value);
            pushSToPollS();
        }

        public T poll() throws Exception {
            if (stackPop.isEmpty() && stackPush.isEmpty()) {
                throw new Exception("Queue is empty!");
            }
            pushSToPollS();
            return stackPop.pop();
        }

        public T peek() throws Exception {
            if (stackPop.isEmpty() && stackPush.isEmpty()) {
                throw new Exception("Queue is empty!");
            }
            pushSToPollS();
            return stackPop.peek();
        }

        //pop栈为空时，把数据从push栈倒入poll栈
        private void pushSToPollS(){
            if(stackPop.isEmpty()) {
                while (!stackPush.isEmpty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        QStack test = new QStack();
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
