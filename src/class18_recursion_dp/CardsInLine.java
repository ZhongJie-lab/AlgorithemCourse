package class18_recursion_dp;

/*
给定一个整型数组arr，代表数值不同的纸牌排成一条线
玩家A和玩家B依次拿走每张纸牌
规定玩家A先拿，玩家B后拿
但是每个玩家每次只能拿走最左或最右的纸牌
玩家A和玩家B都绝顶聪明
请返回最后获胜者的分数。
 */
public class CardsInLine {
    //暴力递归
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int upperHand = f(arr, 0, arr.length - 1); //先手得到的分数- 递归函数
        int backHand = g(arr, 0, arr.length - 1); //后手得到的分数 - 递归函数
        return Math.max(upperHand, backHand);
    }

    //[L,R] 先手获得的最好分数
    private static int f(int[] arr, int L, int R) {
        if (L == R) return arr[L];
        int p1 = arr[L] + g(arr, L + 1, R);
        int p2 = arr[R] + g(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    //[L, R] 后手获得的最好分数
    private static int g(int[] arr, int L, int R) {
        if (L == R) return 0; //作为后手，先手会拿掉arr[L]，作为后手你只能得到0
        int p1 = f(arr, L + 1, R); //假定先手拿掉了arr[L]，作为后手，你接下来可以以先手次态去拿[L+1, R]上的
        int p2 = f(arr, L, R - 1); //假定先手拿掉了arr[R]，作为后手，你接下来可以以先手姿态去拿[L, R+1]上的
        return Math.min(p1, p2); //对手一定会让你得到全力以赴情况下最小的一个，因为玩家都是绝顶聪明
    }

    // f , g 每次递归只有2个变量有关L,R
    // 递归过程中有重复解，可以用记忆化搜索
    // f(0,7) ->
    // g(1, 7)          g(0,6) ->
    // f(2, 7) f(1,6)   f(1, 6) f(0, 5)

    //动态规划 - 傻缓存表
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int upperHand = f2(arr, 0, arr.length - 1, fmap, gmap); //先手递归函数
        int backHand = g2(arr, 0, arr.length - 1, fmap, gmap); //后手递归函数
        return Math.max(upperHand, backHand);
    }


    private static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if(fmap[L][R] != -1) return fmap[L][R];

        int ans;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[L][R] = ans;
        return ans;
    }

    private static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) return gmap[L][R];

        int ans;
        if (L == R) {
            ans = 0;
        } else {
            int p1 = f2(arr, L + 1, R, fmap, gmap);
            int p2 = f2(arr, L, R - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    //动态规划 - 范围尝试模型
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        int N = arr.length;

        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];

        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }

        //按对角线去推 L++, R++
        for (int startCol = 1; startCol < N; startCol++) {
            //每次遍历一条对角线
            int L = 0, R = startCol;
            while (R < N) { //隐含着 行L是不会越界的，列会提前越界，所以只要约束列R
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }

        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static int win33(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        int N = arr.length;

        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];

        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }

        for (int startCol = 1; startCol < N; startCol++) {
            //每次遍历一条对角线
            int L = 0, R = startCol; //L - row; R - col
            while (R < N) {
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }


        public static void main(String[] args) {
//        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        int[] arr = {10,20,30};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }
}
