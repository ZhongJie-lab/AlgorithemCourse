package class43_dp_compression;

// 给定一个1~N的排列，每次将相邻两数相加，可以得到新的序列，长度是N-1
// 再对新的序列，每次将相邻两数相加，可以得到新的序列，长度是N-2
// 这样下去可以最终只剩一个数字
// 比如 :
// 3 1 2 4
//  4 3 6
//   7 9
//    16
// 现在如果知道N，和最后的数字sum，反推最原始的序列是什么
// 如果有多个答案，返回字典序最小的那个
// 字典序看做所有数字拼起来的字符串字典序
// 比如
// 1, 10, 2... 拼起来是 1102...
// 1, 2, 3, 4... 拼起来是 1234...
// 认为 1, 10, 2...的字典序更小
// 如果给定的n和sum，有答案，返回一个N长度的答案数组
// 如果给定的n和sum，无答案，返回一个1长度的数组{ -1 }
// 输入 : N = 4, sum = 16
// 输出 : 3 1 2 4
// 输入 : N = 10, sum = 4116
// 输出 : 1 3 5 7 10 9 8 6 4 2
// 0 < n <= 10, sum随意
public class LexicographicalSmallestPermutation {
    //factorTriangle[i] - [1, i]个数字，加到只有一个数字，每个数的系数 - 杨辉三角形
    public static int[][] factorTriangle = {
            {},
            {1},
            {1, 1},
            {1, 2, 1},
            {1, 3, 3, 1},
            {1, 4, 6, 4, 1},
            {1, 5, 10, 10, 5, 1},
            {1, 6, 15, 20, 15, 6, 1},
            {1, 7, 21, 35, 35, 21, 7, 1},
            {1, 8, 28, 56, 70, 56, 28, 8, 1},
            {1, 9, 36, 84, 126, 126, 84, 36, 9, 1}
    };

    //sums[i] - [1, i]个数字，最大和不会超过sums[i]
    //决定了rest的边界
    public static int sums[] = {0, 1, 3, 9, 24, 61, 148, 350, 808, 1837, 4116};

    public static int[] lsp(int n, int sum) {
        if (n < 1 || n > 10 || sum > sums[n]) {
            return new int[]{-1};
        }

        int[] ans = new int[n];
        //dp[i][j] - dp[status][rest]
        //不会超过 1 << (n + 1)种状态
        //rest不会超过sums[n]
        int[][] dp = new int[1 << (n + 1)][sums[n] + 1];

        if (process(((1 << (n + 1)) - 1) ^ 1, sum, 0, n, factorTriangle[n], dp)) {
            //处理dp
            int ind = 0;
            int status = ((1 << (n + 1)) - 1) ^ 1;
            int rest = sum;
            while (status != 0) {
                ans[ind] = dp[status][rest];
                status ^= (1 << ans[ind]); //从status中去掉数字ans[ind]
                rest -= ans[ind] * factorTriangle[n][ind];
                ind++;
            }
            return ans;
        }
        return new int[]{-1};
    }

    //status - 用位表示状态，还存在的数字，第i位， 0 不存在，1 存在
    //rest - 还剩多少和要搞定
    //ind - 当前的系数 - status里面的某个数字拿出来，要乘以系数，当前系数就是factor[ind]
    private static boolean process(int status, int rest, int ind, int n, int[] factor, int[][] dp) {
        if (rest < 0) return false;

        if (status == 0) {
            return rest == 0;
        }

        //dp[status][rest] == 0 之前没算过 status，rest的情况
        //dp[status][rest] == -1 之前算过 status，rest的情况，返回了false
        //dp[status][rest] != -1 之前算过 status，rest的情况，返回了true
        //  dp[status][rest] 是哪个数字为首试出来的
        if (dp[status][rest] != 0) {
            return dp[status][rest] != -1;
        }

        int ans = -1;
        //为了获得最小字典序，有10的要从10开始尝试，没有10，则优先1，2，3，...，9
        if (n == 10 && ((1 << 10) & status)!= 0) {
            if (process(((1 << 10) ^ status), rest - factor[ind] * 10, ind + 1, n, factor, dp)) {
                ans = 10;
            }
        }
        //没有10，或10没试成功
        if (ans == -1) {
            for (int i = 1; i <= n; i++) { //从小的开始试
                if ( ((1 << i) & status) != 0) {
                    if (process(((1 << i) ^ status), rest - factor[ind] * i, ind + 1, n, factor, dp)) {
                        ans = i;
                        break;
                    }
                }
            }
        }
        dp[status][rest] = ans;
        return ans != -1;
    }

    public static int maxSum(int n) {
        int[] arr = new int[n];
        int[] factor = factorTriangle[n];
        int i = 0, j = n - 1;
        int start = 1;
        int ans = 0;
        while (i < j && start <= n) {
            arr[i] = start * factor[i];
            arr[j] = ++start * factor[j];
            ans += arr[i] + arr[j];
            i++;
            j--;
            start++;
        }
        if (i == j) ans += start * factor[i];
        return ans;
    }

    public static void main(String[] args) {
//        for (int n = 1; n <= 10; n++) {
//            System.out.print(maxSum(n) + ",");
//        }

        int[] ans1 = lsp(4, 16);
        int[] ans2 = lsp(10, 4116);
        int[] ans3 = lsp(10, 3688);
        int[] ans4 = lsp(10, 4013);
        for (int num : ans1) {
            System.out.print(num + " ");
        }
        System.out.println();

        for (int num : ans2) {
            System.out.print(num + " ");
        }
        System.out.println();

        for (int num : ans3) {
            System.out.print(num + " ");
        }
        System.out.println();

        for (int num : ans4) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
