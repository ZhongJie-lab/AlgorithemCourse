package class20_recursion_dp;

/*
请同学们自行搜索或者想象一个象棋的棋盘，
然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
给你三个 参数 x，y，k
返回“马”从(0,0)位置出发，必须走k步
最后落在(x,y)上的方法数有多少种?

  10 * 9 的象棋棋盘，马一开始在初始位置[0, 0]，给定k步和目标位置[a, b] 问有几种跳法能到达目标位置
 */
public class HorseJump {
    public static int jump(int a, int b, int k) {
        return process(0, 0, k, a, b);
    }

    //还有rest步，现在在[x, y]，返回到[a,b]的方法数
    private static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0; //找到一种跳法
        }
        int ways = process(x - 2, y + 1, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);

        return ways;
    }

    public static int jump2(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1]; //[i, j] 马所在棋盘位置；k - 还剩几步
        dp[a][b][0] = 1;

        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = getDp(x - 2, y + 1, rest - 1, dp);
                    ways += getDp(x - 1, y + 2, rest - 1, dp);
                    ways += getDp(x + 1, y + 2, rest - 1, dp);
                    ways += getDp(x + 2, y + 1, rest - 1, dp);
                    ways += getDp(x + 2, y - 1, rest - 1, dp);
                    ways += getDp(x + 1, y - 2, rest - 1, dp);
                    ways += getDp(x - 1, y - 2, rest - 1, dp);
                    ways += getDp(x - 2, y - 1, rest - 1, dp);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    private static int getDp(int x, int y, int rest, int[][][] dp) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }


    public static void main(String[] args) {
        int a = 7, b = 7;
        int step = 10;

        System.out.println(jump(a, b, step));
        System.out.println(jump2(a, b, step));
    }
}
