package class23_recursion_dp;

/*
给定一个正数数组arr，请把arr中所有的数分成两个集合
如果arr长度为偶数，两个集合包含数的个数要一样多
如果arr长度为奇数，两个集合包含数的个数必须只差一个
请尽量让两个集合的累加和接近
返回：
最接近的情况下，较小集合的累加和
 */
public class SplitNumberClosedOddEven {
    public static int splitTwoClosedOddEven(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if ((N & 1) == 0) { //等价于 N % 2 == 0
            return process(nums, 0, N / 2, sum / 2);
        } else {
            return Math.max(process(nums, 0, N / 2 + 1, sum / 2),
                    process(nums, 0, N / 2, sum / 2));
        }
    }

    //nums[i...]自由选择数字，但一定要选满picks个, 且离rest最近的，累加和返回
    private static int process(int[] nums, int index, int picks, int rest) {
        if (index == nums.length) { //没有数字可选了
            return picks == 0 ? 0 : -1; //没选满picks个就没数字了，说明路径不存在，返回-1，选满了那就是0
        }
        int p1 = process(nums, index + 1, picks, rest);
        int p2 = 0;
        if (picks > 0 && rest >= nums[index]) {
            int next = process(nums, index + 1, picks - 1, rest - nums[index]);
            if (next != -1) {
                p2 = nums[index] + next;
            }
        }
        return Math.max(p1, p2);
    }

    public static int dp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        sum /= 2;
        int M = (N + 1) / 2; //向下取整

        int[][][] dp = new int[N + 1][M + 1][sum + 1];
        //初始化dp
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    dp[i][picks][rest] = dp[i + 1][picks][rest];
                    if (picks > 0 && rest >= nums[i]) {
                        if (dp[i + 1][picks - 1][rest - nums[i]] != -1) {
                            dp[i][picks][rest] = Math.max(dp[i][picks][rest], nums[i] + dp[i + 1][picks - 1][rest - nums[i]]);
                        }
                    }
                }
            }
        }

        if (N % 2 == 0) {
            return dp[0][N / 2][sum];
        } else {
            return Math.max(dp[0][N / 2][sum], dp[0][N / 2 + 1][sum]);
        }
    }

    //for test
    public  static void printArray(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static int[] randomArray(int len, int maxVal) {
        int[] arr = new int[len];
        for (int i = 0;i < arr.length; i++) {
            arr[i] = (int)(Math.random() * maxVal);
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxVal = 50;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int)(Math.random() * maxLen);
            int[] arr = randomArray(len, maxVal);
            int ans1 = splitTwoClosedOddEven(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                printArray(arr);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
