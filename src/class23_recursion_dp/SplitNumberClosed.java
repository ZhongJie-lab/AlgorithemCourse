package class23_recursion_dp;

/*
给定一个正数数组arr，
请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
返回：
最接近的情况下，较小集合的累加和
 */

public class SplitNumberClosed {
    public static int splitTwoClosed(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int n : nums) {
            sum += n;
        }

        //两个集合的累加和要接近，最好的情况就是都等于 sum/2，较小的累计和不会大于sum/2
        return process(nums, 0, sum / 2);
    }

    //nums[i...] 自由选择，返回累加和接近rest，但不超过rest的情况下，最接近的累加和是多少？
    public static int process(int[] nums, int i, int rest) {
        if (i == nums.length)  { //没数可选了
            return 0;
        }

        //1. 不选用nums[i], 2. 选用nums[i]
        int p1 = process(nums, i + 1, rest);
        int p2 = 0;
        if (nums[i] <= rest) { //nums[i]都是正数，因此rest不会到0
            p2 = nums[i] + process(nums, i + 1, rest - nums[i]);
        }
        return Math.max(p1, p2); //最接近rest的，所以取值大的
    }

    public static int process2(int[] nums, int i, int rest) {
        if (i == nums.length) {
            return 0;
        }

        int p1 = process(nums, i + 1, rest);
        int p2 = 0;
        if (nums[i] <= rest) {
            p2 = nums[i] + process(nums, i + 1, rest - nums[i]);
        }

        return Math.max(p1, p2);
    }

    public static int dp1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        sum /= 2;

        int N = nums.length;
        int[][] dp = new int[N + 1][sum + 1]; //dp[N][rest] dp[i][0]都是0

        for (int ind = N - 1; ind >= 0; ind--) {
            for (int rest = 1; rest <= sum; rest++) {
                dp[ind][rest] = dp[ind + 1][rest];
                if (nums[ind] <= rest) {
                    dp[ind][rest] = Math.max(dp[ind][rest], nums[ind] + dp[ind + 1][rest - nums[ind]]);
                }
            }
        }

        return dp[0][sum];
    }

    public static int dp11(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        sum /= 2;

        int N = nums.length;
        int[][] dp = new int[N + 1][sum + 1];
        //dp[N][i]是0

        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 1; rest <= sum; rest++) {
                int p1 = dp[i + 1][rest];
                int p2 = 0;
                if (nums[i] <= rest) {
                    p2 = nums[i] + dp[i + 1][rest - nums[i]];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
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
            int ans1 = splitTwoClosed(arr);
            int ans2 = dp1(arr);
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
