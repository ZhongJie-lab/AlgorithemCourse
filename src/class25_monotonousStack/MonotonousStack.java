package class25_monotonousStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MonotonousStack {
    //对每一个数，求出左边离它最近的比它小的，右边离它最近的比它小的
    /*
     3 4 2 6 1 7 0
     0->3 左边最近的比它小的-1， 右边最近的比它小的2->2
     1->4 左边最近的比它小的0->3， 右边最近的比它小的2->2
     2->2 左边最近的比它小的-1， 右边最近的比它小的4->1
     3->6
     4->1
     5->7
     6->0
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] res = new int[arr.length][2];
        //栈底到栈顶，从小到大，栈只存数组下标
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int j = stack.pop();
                int leftLessInd = stack.isEmpty() ? -1 : stack.peek();
                res[j][0] = leftLessInd;
                res[j][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftLessInd = stack.isEmpty() ? -1 : stack.peek();
            res[j][0] = leftLessInd;
            res[j][1] = -1;
        }
        return res;
    }

    public static int[][] getNearLess(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<LinkedList<Integer>> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().getLast()] > arr[i]) {
                List<Integer> posIds = stack.pop();
                int leftLessInd = stack.isEmpty() ? -1 : stack.peek().getLast();
                for (int posId : posIds) {
                    res[posId][0] = leftLessInd;
                    res[posId][1] = i;
                }
            }

            if (!stack.isEmpty() && arr[stack.peek().getLast()] == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.add(i);
                stack.push(list);
            }
        }

        while (!stack.isEmpty()) {
            List<Integer> posIds = stack.pop();
            int leftLessInd = stack.isEmpty() ? -1 : stack.peek().getLast();
            for (int posId : posIds) {
                res[posId][0] = leftLessInd;
                res[posId][1] = -1;
            }
        }
        return res;
    }

    //for test  -暴力解
    public static int[][] rightWay(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessInd = -1;
            int rightLessInd = -1;

            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessInd = cur;
                    break;
                }
                cur--; //往左试探
            }

            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessInd = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessInd;
            res[i][1] = rightLessInd;
        }

        return res;
    }

    // for test
    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 20;
        int testTimes = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
                System.out.println("Oops1!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops2!");
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
