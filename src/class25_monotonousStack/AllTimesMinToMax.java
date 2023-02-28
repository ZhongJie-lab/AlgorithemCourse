package class25_monotonousStack;

import java.util.Stack;

/*
    给定一个只包含正数的数组arr，arr中任何一个子数组sub，
    一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
    那么所有子数组中，这个值最大是多少？
 */
public class AllTimesMinToMax {
    //枚举以每一个arr[i]为最小值的结果，取其中结果最大的
    //每一个arr[i]为最小值的结果，这里就可以用单调栈实现

    public static int max(int[] arr) {
        int size = arr.length;
        //前缀和数组
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i< size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        int ans = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) { //相等时会算错，但是最后一个相等的数计算时是能算出最好答案，所以不会错过最好答案
                int j = stack.pop(); //以arr[j]为最小值计算，左边界：栈顶元素，右边界：i，j所要计算的范围是 (stack.peek(), i - 1]
                ans = Math.max(ans, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop(); //没有右边界，j 所在的范围就是 (stack.peek(), size - 1]
            ans = Math.max(ans, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
        }
        return ans;
    }

    //暴力解
    public static int right(int[] arr) {
        int ans = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                ans = Math.max(ans, minNum * sum);
            }
        }

        return ans;
    }

    //for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 200;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            int ans1 = max(arr);
            int ans2 = right(arr);
            if (ans1 != ans2) {
                System.out.println("FUCK!");
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test finish");
    }
}
