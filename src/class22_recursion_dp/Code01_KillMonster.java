package class22_recursion_dp;

/*
给定3个参数，N，M，K
怪兽有N滴血，等着英雄来砍自己
英雄每一次打击，都会让怪兽流失[0~M]的血量
到底流失多少？每一次在[0~M]上等概率的获得一个值
求K次打击之后，英雄把怪兽砍死的概率
 */
public class Code01_KillMonster {
    public static double killRate(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }

        long all = (long)Math.pow(M + 1, K); // 总的路径数量
        long kill = process(K, M, N); // 结果是怪兽被砍死的路径数量
        return (double)kill / (double) all;
    }

    //怪兽还剩hp滴血，还有times刀，返回砍死的情况数
    private static long process(int times, int M, int hp) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) { //已经砍死了，提前计算剩下的所有路径
            return (long)Math.pow(M + 1, times);
        }
        long ways = 0;
        for (int i = 0; i <= M; i++) {
            ways += process(times - 1, M, hp - i);
        }
        return ways;
    }

    public static double killRateDp(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }

        long all = (long)Math.pow(M + 1, K); // 总的路径数量
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1; // 默认第0行，除了[0][0]是1，其余是0
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long)Math.pow(M + 1, times); //第0列，0滴血，提前计算剩下所有路径
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i > 0) {
                        ways += dp[times - 1][hp - i];
                    } else { //砍了这一刀以后，出现hp <= 0, 则提前计算剩下所有路径
                        ways += Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }


        long kill = dp[K][N];
        return (double)kill / (double) all;
    }

    public static double killRateDp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long)Math.pow(M + 1, K); // 总的路径数量
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1; // 默认第0行，除了[0][0]是1，其余是0

        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                //dp[i][j - 1] = dp[i - 1][j - 1 .... j - 1 - M]
                //dp[i][j] = dp[i - 1][j .... j - M]
                //dp[i][j] = dp[i][j - 1] + dp[i - 1][j] - dp[i - 1][j - 1 - M]
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                //此处还要剪掉 dp[3][4] = dp[2][4...-1] dp[3][3] = dp[2][3...-2]
                // dp[3][4] = dp[3][3] + dp[2][4] - dp[2][-2]
                if (hp - 1 - M >= 0) { //
                    dp[times][hp] -= dp[times][hp - 1 - M];
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) kill / (double) all;
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 20;
        System.out.println("Start test");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = killRate(N, M, K);
            double ans2 = killRateDp(N, M, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(N + " " + M + " " + K);
                break;
            }

        }
        System.out.println("End test");
    }
}
