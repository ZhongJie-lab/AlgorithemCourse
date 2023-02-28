package class21_recursion_dp;

/*
给定5个参数，N，M，row，col，k
表示在N*M的区域上，醉汉Bob初始在(row,col)位置
Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
任何时候Bob只要离开N*M的区域，就直接死亡
返回k步之后，Bob还在N*M的区域的概率
 */
public class BobDie {
    public static double livePosibility(int N, int M, int row, int col, int k) {
        //假设棋盘无限大，k步能走的方式一共是 4的k次方，每一步都是4个方向可选择
        return (double) process(N, M, row, col, k) / Math.pow(4, k);
    }

    private static long process(int N, int M, int row, int col, int rest) {
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        if (rest == 0) { //还在棋盘中
            return 1;
        }

        long up = process(N, M, row - 1, col, rest - 1);
        long down = process(N, M, row + 1, col, rest - 1);
        long left = process(N, M, row , col - 1, rest - 1);
        long right = process(N, M, row, col + 1, rest - 1);

        return up + down + left + right;
    }

    public static double livePosibility2(int N, int M, int row, int col, int k) {
        long[][][] dp = new long[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] = pick(dp, N, M, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c + 1, rest - 1);
                }
            }
        }

        return (double) dp[row][col][k]/ Math.pow(4, k);
    }

    private static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M) {
            return 0;
        }

        return dp[r][c][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePosibility(50, 50, 6, 6, 10));
        System.out.println(livePosibility2(50, 50, 6, 6, 10));
    }
}
