package class17_recursion_dp;


import java.util.Stack;

//逆序这个栈
public class ReverseStackUsingRecursive {
    //不申请额外空间，只用递归，逆序这个栈
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }
        int i = getBottomEle(stack);
        reverse(stack);
        stack.push(i);
    }

    // 栈底元素移除掉
    // 上面的元素盖下来
    // 返回移除掉的栈底元素
    private static int getBottomEle(Stack<Integer> stack) {
        int num = stack.pop();
        if (stack.isEmpty()) {
            return num;
        } else {
            int last = getBottomEle(stack);
            stack.push(num);
            return last;
        }
    }

    public static void reverse2(Stack<Integer> stack) {
        if (stack.isEmpty()) return;

        int num = getBottomEle2(stack);
        reverse(stack);
        stack.push(num);
    }

    private static int getBottomEle2(Stack<Integer> stack) {
        int num = stack.pop();
        if (stack.isEmpty()) {
            return num;
        } else {
            int last = getBottomEle2(stack);
            stack.push(num);
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        reverse(stack);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
