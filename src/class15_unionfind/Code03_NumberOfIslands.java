package class15_unionfind;

//https://leetcode.cn.com/problems/number-of-islands/
public class Code03_NumberOfIslands {
    //方法一：感染 O(n * m)
    public static int numIslands(char[][] board) {
        int islands = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') { //该位置是岛，去感染周边，都标记为2，相当于把连城一片的1都改成2，相当于发现一个岛屿
                    islands++;
                    infect(board, i, j);
                }
            }
        }
        return islands;
    }

    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        //该位置是岛，开始感染
        board[i][j] = '2';
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }

    public static int numIslands2(char[][] board) {
        int ans = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) {
                    ans++;
                    infect2(board, i, j);
                }
            }
        }
        return ans;
    }

    private static void infect2(char[][] board, int r, int c) {
        if (r < 0 || r >= board.length || c < 0 || c > board[0].length || board[r][c] != '1') {
            return;
        }
        board[r][c] = '2';
        infect2(board, r - 1, c);
        infect2(board, r, c - 1);
        infect2(board, r + 1, c);
        infect2(board, r, c + 1);
    }

    //方法二：并查集
    public static int numsIslands2(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        UnionFind uf = new UnionFind(grid);
        for (int j = 1; j < cols; j++) {
            if (grid[0][j-1] == '1' && grid[0][j] == '1')
                uf.uion(0, j - 1, 0, j);
        }
        for (int i = 1; i < rows; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1')
                uf.uion(i - 1, 0, i, 0);
        }
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        uf.uion(i, j - 1, i, j);
                    }
                    if (grid[i - 1][j] == '1') {
                        uf.uion(i - 1, j, i, j);
                    }
                }
            }
        }
        return uf.sets;
    }

    public static class UnionFind{
        int[] parents;
        int[] size;
        int[] help;
        int sets;
        int row, col;

        public UnionFind(char[][] grid) {
            row = grid.length;
            col = grid[0].length;

            this.parents = new int[row * col];
            this.size = new int[row * col];
            this.help = new int[row * col];

            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (grid[r][c] == '1') {
                        int ind = index(r, c);
                        parents[ind] = ind;
                        size[ind] = 1;
                        sets++;
                    }
                }
            }
        }

        public int find(int i) {
            int j = 0;
            while (i != parents[i]) {
                help[j++] = i;
                i = parents[i];
            }

            while (--j >= 0) {
                parents[help[j]] = i;
            }
            return i;
        }

        public void uion(int r1, int c1, int r2, int c2) {
            int ind1 = index(r1, c1);
            int ind2 = index(r2, c2);

            int p1 = find(ind1);
            int p2 = find(ind2);

            if (p1 == p2) {
                return;
            }

            if (size[p1] >= size[p2]) {
                size[p1] += size[p2];
                parents[p2] = p1;
            } else {
                size[p2] += size[p1];
                parents[p1] = p2;
            }
            sets--;
        }

        public int sets() {
            return sets;
        }

        private int index(int r, int c) {
            return r * col + c;
        }

    }
}
