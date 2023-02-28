package class43_dp_compression;

//https://leetcode.cn/problems/can-i-win/
public class CanIWin {
    //choose 拥有的数字
    //total 一开始的剩余

    //暴力递归 - 超时
    public static boolean canIWin0(int choose, int total) {
        if (total == 0) {
            return false;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        int[] arr = new int[choose];
        for (int i = 0; i < choose; i++) {
            arr[i] = i + 1;
        }
        //arr[i] != -1 表示arr[i]这个数字还没被拿走
        //arr[i] == -1 表示arr[i]这个数字已经被拿走

        return process0(arr, total);
    }

    //当前轮到先手拿
    //先手只能选择arr中还存在的数字
    //还剩rest
    //返回先手会不会赢
    private static boolean process0(int[] arr, int rest) {
        if (rest <= 0) return true;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                int cur = arr[i];
                arr[i] = -1;
                boolean next = process0(arr, rest - cur); //返回当前的后手会不会赢
                arr[i] = cur; //恢复现场
                if (!next) { //后手输了，说明我赢了
                    return true;
                }
            }
        }
        return false;
    }

    //可变参数一维线性结构arr用一个int替代，每一个bit代表数字的状态
    public static boolean canIWin1(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        return process1(choose, 0, total);
    }

    //当前轮到先手拿
    //先手只能选择1 ~ choose中是数字
    // status
    //     i位如果为0，代表没拿，当前可以拿；i位如果为1，代表已经拿过了，当前不能拿
    //     从第1位开始标记状态，第0位弃而不用
    //还剩rest
    //返回先手会不会赢
    private static boolean process1(int choose, int status, int rest) {
        if (rest <= 0) return false;

        for (int i = 1; i <= choose; i++) {
            if (((1 << i) & status) == 0) {
                if (!process1(choose, status | (1 << i), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    //动态规划 - 傻缓存
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }

        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }

        int[] dp = new int[1 << (choose + 1)]; //e.g. choose: 1,2,3,4,5 所有状态 0 ~ 11111； 进一步，第0位弃而不用，从第1位对应数字1的状态，所有状态 0 ~ 111111，一共是1000000个状态
        // dp[status] == 1 true
        // dp[status] == -1 false
        // dp[status] == 0 这个status的process 还没算过
        return process2(choose, 0, total, dp);
    }

    //rest由status决定，rest和status不是独立状态
    private static boolean process2(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }

        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) {
                    if (!process2(choose, status | (1 << i), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }
}
