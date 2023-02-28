package class21_recursion_dp;

/*
    给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
    沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
    返回最小距离累加和
 */

public class MinPathSum {
    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;

        int[][] dp = new int[row][col]; //dp[i][j]从[0][0]走到[i][j]的最少累加和
        dp[0][0] = m[0][0];

        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    //空间优化， 二维数组优化成一维数组（滚动数组）
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;

        int[] arr = new int[col];
        arr[0] = m[0][0];

        //搞定第一行
        for (int j = 1; j < col; j++) {
            arr[j] = arr[j - 1] + m[0][j];
        }
        //搞定剩余行
        //dp[0][...] -> arr
        //arr[0] -> dp[上一行][0]
        //求 dp[这一行][0]
        //以及，求 dp[这一行][...]
        for (int i = 1; i < row; i++) {
            arr[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                //arr[j-1] -> dp[这一行][j-1] 左侧的值
                //arr[j] -> dp[上一行][j] 上侧的值
                arr[j] = Math.min(arr[j], arr[j - 1]) + m[i][j];
            }
        }
        return  arr[col - 1];
    }

    public static void main(String[] args) {
        int[][] m = new int[][] {
                {1,3,4},
                {2,6,1},
                {3,1,0}
        };

        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
    }
}
