package class23_recursion_dp;

//n皇后问题是经典暴力过程，时间复杂度 O(n的n次方)
public class NQueens {
    public static int nums1(int n) {
        if (n  < 1) {
            return 0;
        }
        //轨迹信息，[[0, 9] [1, 5] [2, 13]] 0行皇后放在第9列上，1行皇后放在第5列上，2行皇后放在第13列上
        // 一行一行地放皇后，可以直接实现不能共行（每次放一个皇后），如何判断共列？共斜线？
        int[] record = new int[n]; //轨迹信息，一行一行地放皇后，可以直接实现不能共行（每次放一个皇后），如何判断共列？共斜线？
        //record数组不需要回溯，如果一条路不通向上返回，下面行数据脏了，不用清理，因为决策只看上面行的数据
        return process(0, record, n);
    }

    //当前来到i行，在i行上放皇后
    //必须保证和之前所有皇后都不打架
    //record 记录了之前皇后的摆放位置，record[x] = y 之前的第x行皇后，摆放在了第y列上
    //返回，不关心i以上发生了什么，i..后续有多少种合法的方法数
    private static int process(int i, int[] record, int n) {
        if (i == n) { //i越界了，说明全部摆放完，说明找到一种方法
            return 1;
        }
        int res = 0;

        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process(i + 1, record, n);
            }
        }
        return res;
    }

    //i 当前尝试的行，j 当前尝试的列，
    // 验证[i,j]是否是和前面所有行的皇后不冲突的位置
    private static boolean isValid(int[] record, int i, int j) {
        //之前的皇后 0...i-1 行
        for (int k = 0; k < i; k++) {
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(k - i)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 8;

        System.out.println(nums1(n));
    }
}
