package class43_dp_compression;

import java.util.ArrayList;
import java.util.List;

/*
TSP问题 有N个城市，任何两个城市之间的都有距离，任何一座城市到自己的距离都为0。
所有点到点的距离都存在一个N*N的二维数组matrix里，也就是整张图由邻接矩阵表示。
现要求一旅行商从k城市 出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的k城，返回总距离最短的路的 距离。
参数给定一个matrix，给定k。
 */
public class TSP {

    // 任何两座城市之间的距离，可以在matrix里面拿到
    // set中表示着哪些城市的集合，

    public static int t1(int[][] matrix) {
        int N = matrix.length;

        //set.get(i) != null i这座城市在集合里
        //set.get(i) == null i这座城市不在集合里
        //初始，所有城市都在集合里
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            arr.add(1);
        }

        return func1(matrix, arr, 0);
    }

    //start 这座城市一定在集合里
    //从start这座城市出发，把set中所有的城市都走过一遍，最终回到0这座城市，最小距离是多少？
    private static int func1(int[][] matrix, List<Integer> arr, int start) {
        int cityNum = 0;
        //有几座城市在集合中
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != null) {
                cityNum++;
            }
        }
        if (cityNum == 1) { //唯一的城市必是start
            return matrix[start][0];
        }

        //cityNum > 1, 不只start这一座城市
        arr.set(start, null); //从start出发
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.size(); i++) {//再从集合中挑一座未走过的城市出发
            if (arr.get(i) != null) {
                int cur = matrix[start][i] + func1(matrix, arr, i);
                min = Math.min(min, cur);
            }
        }
        arr.set(start, 1); //恢复现场，set是引用传递的
        return min;
    }

    //arr用int表示 0 - 不在集合里，1 - 在集合里
    public static int t2(int[][] matrix) {
        int N =matrix.length;

        int allCities = (1 << N) - 1;
        return func2(matrix, allCities, 0);
    }

    private static int func2(int[][] matrix, int cityStatus, int start) {
        //只剩一座城市 (cityStatus & (~cityStatus + 1))把最右的1拿出来
        if (cityStatus == ((cityStatus & (~cityStatus + 1)))) {
            return matrix[start][0];
        }

        //把start位从1变成0
        cityStatus &= (~(1 << start));
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            //第i座城市cityStatus & (1 << i)
            if (i != start && (cityStatus & (1 << i)) != 0) {
                int cur = matrix[start][i] + func2(matrix, cityStatus, i);
                min = Math.min(min, cur);
            }
        }
        cityStatus |= (1 << start); //把start位恢复为1，恢复现场
        return min;
    }

    //dp 记忆化搜索
    public static int t3(int[][] matrix) {
        int N =matrix.length;

        //6座城，初始状态 111111， 一共多少状态 0 ~ 111111， 1000000个状态
        int allCities = (1 << N) - 1;

        int[][] dp = new int[(1 << N)][N];
        //初始化dp
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1;
            }
        }

        return func3(matrix, dp, allCities, 0);
    }

    private static int func3(int[][] matrix, int[][] dp, int cityStatus, int start) {
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }

        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            dp[cityStatus][start] = matrix[start][0];
        } else {
            cityStatus &= (~(1 << start));
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                if (i != start && (cityStatus & (1 << i)) != 0) {
                    int cur = matrix[start][i] + func3(matrix, dp, cityStatus, i);
                    min = Math.min(min, cur);
                }
            }

            cityStatus |= (1 << start);
            dp[cityStatus][start] =min;
        }
        return dp[cityStatus][start];
    }

    //dp 完全位置依赖
    public static int t4(int[][] matrix) {
        int N =matrix.length;

        int statusNums = 1 << N;

        int[][] dp = new int[(1 << N)][N];
        //初始化dp
        // dp[0][k] = 0 所有城市都走过了，那么从任何城市出发，代价都是0
        //从上往下推
        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {
                if ((status & (1 << start)) != 0) { //start这个城市还没走过，可以走
                    //如果只有一座城市没走过
                    if (status == (status & (~status + 1))) {
                        dp[status][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;

                        int preStatus = status & (~(1 << start)); //去掉start之后的status
                        //start -> i
                        for (int i = 0; i < N; i++) {
                            if ((preStatus & (1 << i)) != 0) { //如果i还在集合中， preStatus的第i位是1
                               int cur = matrix[start][i] + dp[preStatus][i];
                               min= Math.min(min, cur);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }

        return dp[statusNums - 1][0];
    }

    //for test
    public static int[][] generateGraph(int maxSize, int maxValue) {
        int N = (int)(Math.random() * maxSize) + 1;
        int[][] matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = (int)(Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < N; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 1000;

//        System.out.println("功能测试开始");
//        for (int i = 0; i < 2000; i++) {
//            int[][] matrix = generateGraph(N, value);
//            int ans0 = t1(matrix);
//            int ans1 = t2(matrix);
//            int ans2 = t3(matrix);
//            int ans3 = t4(matrix);
//            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans0) {
//                System.out.println("Fail");
//                System.out.println("ans0 = " + ans0);
//                System.out.println("ans1 = " + ans1);
//                System.out.println("ans2 = " + ans2);
//                System.out.println("ans3 = " + ans3);
//                printMatrix(matrix);
//                break;
//            }
//        }
//        System.out.println("功能测试结束");

        System.out.println("性能测试开始");
        long start;
        long end;
        int[][] matrix = generateGraph(20, value);
        start = System.currentTimeMillis();
        t2(matrix);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");

        start = System.currentTimeMillis();
        t4(matrix);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");

        System.out.println("性能测试结束");
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int a : ints) {
                System.out.print(a + ", ");
            }
            System.out.println();
        }
    }
}
