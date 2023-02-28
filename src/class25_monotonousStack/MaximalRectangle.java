package class25_monotonousStack;

import java.util.Stack;
//https://leetcode-cn.com/problems/maximal-rectangle/
/*
    给定一个二维数组matrix，其中的值不是0就是1，
    返回全部由1组成的最大子矩形，内部有多少个1
 */
public class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        //以第一行为底，最大子矩形
        //以第二行为底，最大子矩形
        //....
        //每一行为底时，高可以知道，则下面就是单调栈解决直方图最大面积即可
        int maxArea = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[] height = new int[M];

        for (int i = 0; i < N; i++) {
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j < M; j++) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, maxFromBottom(height));
        }

        return maxArea;
    }

    private int maxFromBottom(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                maxArea = Math.max(maxArea, (i - k - 1) * height[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            maxArea = Math.max(maxArea, (height.length - k - 1) * height[j]);
        }
        return maxArea;
    }

    private int maxFromBottom2(int[] height) {
        int[] stack = new int[height.length];
        int sInd = -1;
        int maxArea = 0;
        for (int i = 0; i < height.length; i++) {
            while (sInd != -1 && height[stack[sInd]] >= height[i]) {
                int j = stack[sInd--];
                int k = sInd == -1 ? -1 : stack[sInd];
                maxArea = Math.max(maxArea, (i - k - 1) * height[j]);
            }
            stack[++sInd] = i;
        }
        while (sInd != -1) {
            int j = stack[sInd--];
            int k = sInd == -1 ? -1 : stack[sInd];
            maxArea = Math.max(maxArea, (height.length - k - 1) * height[j]);
        }
        return maxArea;
    }
}
