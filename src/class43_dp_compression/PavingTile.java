package class43_dp_compression;

/*
你有无限的1*2的砖块，要铺满M*N的区域，
不同的铺法有多少种?
*/
public class PavingTile {
    //暴力递归
    public static int ways1(int N, int M) {
        //1*2的砖块，必是偶数个格子才能贴满 ((N * M) & 1) == 0
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }

        int[] pre = new int[M]; // pre代表前一行的状况，-1行，0表示空，1表示已经贴上了瓷砖
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return process1(pre, 0, N);
    }

    //每一个普遍的格子，只有两种摆放方式，砖块向右摆和向上摆，（向下摆可以等同于下一个格子的向上摆，向左摆等于前一个格子的向右摆）
    //level 当前行，默认level-2行都是满的，level-1行的状态是pre
    //pre 前一行的状态
    //当前来到第level行，可以做出选择，铺满所有0 ~ N-1行的方法数
    private static int process1(int[] pre, int level, int N) {
        //base case 已经出界来到N行
        if (level == N) {
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }

        //当前行的砖块摆放 上面格子为0的，当前砖块必须向上放，上面格子为1的，可以自由选择
        int[] op = getOperation(pre);
        //深度优先遍历自由选择得到的所有摆放方法
        return dfs1(op, 0, level, N);
    }

    private static int dfs1(int[] op, int col, int level, int N) {
        //在col上做决定，当col来到终止列时，level行的决定做完了，去下一行继续
        if (col == op.length) {
            return process1(op, level + 1, N);
        }
        int ans = 0;
        //col位置不横摆，跳过
        ans += dfs1(op, col + 1, level, N);
        //col位置横摆
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            ans += dfs1(op, col + 2, level, N);
            op[col] = 0;
            op[col + 1] = 0;
        }
        return ans;
    }

    //得到当前行的砖块摆放状况：上面格子为0的，当前砖块必须向上放，上面格子为1的，可以自由选择
    private static int[] getOperation(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }

    //暴力递归 - 位运算版本
    public static int ways2(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0 ) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        //小的做列(<= 32，int存放位信息)，大的做行
        int m = Math.min(N, M);
        int n = Math.max(N, M);

        int pre = (1 << m) - 1;
        return process2(pre, 0, n, m);
    }

    private static int process2(int pre, int i, int N, int M) {
        if (i == N) {
            return pre == ((1 << M) - 1) ? 1 : 0;
        }
        int op = (~pre & ((1 << M) - 1));
        //从M - 1列开始
        return dfs2(op, M - 1, i, N, M);
    }

    private static int dfs2(int op, int col, int i, int N, int M) {
        // 如果已经来到终止列，到下一行去
        if (col == -1) {
            return process2(op, i + 1, N, M);
        }
        int ans = 0;
        //跳过当前col，不横摆
        ans += dfs2(op, col - 1, i, N, M);
        //不跳过 如果当前位和前一位都是0，则可以横摆
        if (col - 1 >= 0 && (op & (1 << col)) == 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2(op | (3 << (col - 1)), col - 2, i, N, M);
        }

        return ans;
    }

    //动态规划 - 记忆化搜索
    public static int ways3(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int m = Math.min(N, M);
        int n = Math.max(N, M);

        int pre = (1 << m) - 1;

        int[][] dp = new int[(1 << m)][n + 1]; //dp[i][j] - 第j行做决定，前一行的状态是i，一共有多少种方法铺满
        //初始化dp
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(pre, 0, n, m, dp);
    }

    private static int process3(int pre, int level, int N, int M, int[][] dp) {
        if (dp[pre][level] != -1) {
            return dp[pre][level];
        }
        int ans = 0;
        if (level == N) {
            ans = pre == ((1 << M) - 1) ? 1 : 0;
        } else {
            int op = (~pre & ((1 << M) - 1));
            ans = dfs3(op, M - 1, level, N, M, dp);
        }

        dp[pre][level] = ans;
        return ans;
    }

    private static int dfs3(int op, int col, int level, int N, int M, int[][] dp) {
        if (col == -1) {
            return process3(op, level + 1, N, M, dp);
        }
        int ans = 0;
        ans += dfs3(op, col - 1, level, N, M, dp);
        if (col - 1 >= 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3(op | (3 << (col - 1)), col - 2, level, N, M, dp);
        }
        return ans;
    }

    //动态规划 - 严格位置依赖
    public static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int m = Math.min(N, M);
        int n = Math.max(N, M);

        //TODO
        //dp可以只是一维的，一行一行推
        int sn = 1 << m; //status number 一共几种状态 假设m = 6，0 ~ 111111 = 1000000
        int limit = sn - 1; //摆满的状况 111111
        int[] dp = new int[sn]; //dp[i] 当前行可以做出选择，前一行的状态是i，此时的方法数
        dp[limit] = 1; //dp N行的状况 N-1 行全是满的

        int[] cur = new int[sn]; // 当前行 要算出所有状态下的解
        for (int level = 0; level < n; level++) { //从下往上推的，level没参与计算，只是要算 n-1遍
            for (int status = 0; status < sn; status++) {
                // level == N-1，dp是N行的状况下的方法数
                if (dp[status] != 0) { // 状态出现了 status下有解，下面计算上一行的status‘使之能够支撑当下的解，怎样的status'能得到status
                    int op =(~status) & limit;
                    dfs4(dp[status], op, 0, m - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] =0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    private static void dfs4(int way, int op, int ind, int end, int[] cur) {
        if (ind == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, ind + 1, end, cur);
            if (((3 << ind) & op) == 0) { //可以摆砖块
                dfs4(way, op | (3 << ind), ind + 1, end, cur);
            }
        }
    }


    public static void main(String[] args) {
        int N = 8, M =5;

        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }
}
