package class25_monotonousStack;

import java.util.Stack;

/*
给定一个非负数组arr，代表直方图
返回直方图的最大长方形面积
 */
public class LargestRectangleInHistogram {
    //枚举以每一个长方形为高的最大长方形面积，从结果中再选出最大的
    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) { //相等时也弹出，最后一个相等的数弹出会算正确，不会错过最好答案
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * heights[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (heights.length - k - 1) * heights[j];
            maxArea = Math.max(maxArea, curArea);
        }

        return maxArea;
    }

    public static int largestRectangleArea1(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;

        Stack<Integer> stack = new Stack<>();
        //每个heights[i]，必须以heights[i]为高能扩多远？也就是heights[i]为高，找到它左边离它最近的比它小的，以及右边离它最近的比它小的
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) { //相等时也弹出，最后一个相等的数弹出会算正确，不会错过最好答案
                int j = stack.pop(); //heights[j]是那个需要找的最小值
                int k = stack.isEmpty() ? -1 : stack.peek(); //k左边界, i右边界
                int curArea = (i - k - 1) * heights[j]; //i - k - 1 底边
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek(); //k左边界, heights.length右边界
            int curArea= (heights.length - k - 1) * heights[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }

    //用数组代替JDK自带的stack作为栈
    public static int largestRectangleArea2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int maxArea = 0;
        int[] stack = new int[heights.length];
        int sInd = -1; //栈顶指针
        for (int i = 0; i < heights.length; i++) {
            while (sInd != -1 && heights[stack[sInd]] >= heights[i]) { //相等时也弹出，最后一个相等的数弹出会算正确，不会错过最好答案
                int j = stack[sInd--];
                int k = sInd == -1 ? -1 : stack[sInd];
                int curArea = (i - k - 1) * heights[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack[++sInd] = i;
        }
        while (sInd != -1) {
            int j = stack[sInd--];
            int k = sInd == -1 ? -1 : stack[sInd];
            int curArea = (heights.length - k - 1) * heights[j];
            maxArea = Math.max(maxArea, curArea);
        }

        return maxArea;
    }
}
