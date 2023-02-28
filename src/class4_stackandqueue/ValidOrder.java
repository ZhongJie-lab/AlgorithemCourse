package class4_stackandqueue;

import java.util.Stack;

public class ValidOrder {
    public static boolean validOrder(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) return false;

        Stack<Integer> stack = new Stack<>();

        int ind = 0;
        int N = arr1.length;

        for (Integer n : arr2) {
            if (stack.isEmpty() || stack.peek() != n) {
                for (; ind < N; ind++) {
                        if (arr1[ind] == n) {
                            stack.push(arr1[ind]);
                            ind++;
                            break;
                        }
                    stack.push(arr1[ind]);
                }
                if (stack.isEmpty() || stack.peek() != n) {
                    return false;
                } else {
                    stack.pop();
                }
            } else {
                stack.pop();
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {5, 1, 3, 2, 4};

        System.out.println(validOrder(arr1, arr2));
    }
}
