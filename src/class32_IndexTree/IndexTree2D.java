package class32_IndexTree;

//二维IndexTree
//https://leetcode.com/problems/range-sum-query-2d-mutable 二维区域和检索 - 可变
public class IndexTree2D {
    private int[][] tree;
    private int[][] nums;

    private int N;
    private int M;

    public IndexTree2D(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        N = matrix.length;
        M = matrix[0].length;
        tree = new int[N + 1][M + 1];
        nums = new int[N][M];
        for (int i = 0; i < N; i++) { //准备nums和tree
            for (int j = 0; j < M; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public int sum(int row, int col) {
        int sum = 0;
        for (int i = row + 1; i > 0; i -= i & (-i)) {
            for (int j = col + 1; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    private void update(int row, int col, int val) {
        if (N == 0 || M == 0) {
            return;
        }
        int addVal = val - nums[row][col];
        for (int i = row + 1; i <= N; i += i & (-i)) {
            for (int j = col + 1; j <= M; j += j & (-j)) {
                tree[i][j] += addVal;
            }
        }
    }

    public int sumRegin(int row1, int col1, int row2, int col2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        return sum(row2, col2) - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1, col1 - 1);
    }
}
