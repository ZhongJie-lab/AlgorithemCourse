package class25_monotonousStack;

import java.util.Stack;

//https://leetcode-cn.com/problems/count-submatrices-with-all-ones
/*
给定一个二维数组matrix，其中的值不是0就是1，
返回全部由1组成的子矩形数量
 */
public class CountSubmatricesWithAllOnes {
    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return 0;
        int N = mat.length;
        int M = mat[0].length;

        int[] height = new int[M];
        int num = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                height[j] = mat[i][j] == 0 ? 0 : (height[j] + 1);
            }
            num += countFromBottom(height);
        }
        return num;
    }

    private static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int num = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int curr = stack.pop();
                if (height[curr] > height[i]) { //等于的时候不算，只是弹出，等着最后一个等于的弹出时一起算
                    int leftInd = stack.isEmpty() ? -1 : stack.peek();
                    int w = i - leftInd - 1;
                    int h = height[curr] - Math.max(leftInd == -1 ? 0 : height[leftInd], height[i]); //左右两边比我小的之中取较大的
                    num += ((w * (w + 1)) >> 1) * h;
                }
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int curr = stack.pop();
            int leftInd = stack.isEmpty() ? -1 : stack.peek();
            int w = height.length - leftInd - 1;
            int h = height[curr] - (leftInd == -1 ? 0 : height[leftInd]);
            num += ((w * (w + 1)) >> 1) * h;
        }

        return num;
    }

    private static int countFromBottom2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int num = 0;
        int[] stack = new int[height.length];
        int sInd = -1;
        for (int i = 0; i < height.length; i++) {
            while (sInd != -1 && height[stack[sInd]] >= height[i]) {
                int curr = stack[sInd--];
                if (height[curr] > height[i]) { //等于的时候不算，只是弹出，等着最后一个等于的弹出时一起算
                    int leftInd = sInd == -1 ? -1 : stack[sInd];
                    int w = i - leftInd - 1;
                    int h = height[curr] - Math.max(leftInd == -1 ? 0 : height[leftInd], height[i]); //左右两边比我小的之中取较大的
                    num += ((w * (w + 1)) >> 1) * h;
                }
            }
            stack[++sInd] = i;
        }

        while (sInd != -1) {
            int curr = stack[sInd--];
            int leftInd = sInd == -1 ? -1 : stack[sInd];
            int w = height.length - leftInd - 1;
            int h = height[curr] - (leftInd == -1 ? 0 : height[leftInd]);
            num += ((w * (w + 1)) >> 1) * h;
        }

        return num;
    }

    public static void main(String[] args) {
        int[][] mat = {
                {1,0,1},
                {1,1,0},
                {1,1,0}
        };
        System.out.println(numSubmat(mat));
    }
}
