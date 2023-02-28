package class32_IndexTree;

/*
    IndexTree的特点：
    1. 支持区间查询
    2. 没有线段树那么强，但非常容易改成一维，二维，三维的结构
    3. 只支持单点更新
 */
public class IndexTree_sample {
    //从1位置开始，0弃而不用
    //假设原数组都是0
    public static class IndexTree{
        private int[] tree; //help数组
        private int N;

        public IndexTree(int size) {
            N = size;
            tree = new int[N + 1];
        }

        // 1 ~ index 累加和是多少？
        // 分段求解 例：求arr[1] ~ arr[0110100]的累加和，tree[0110100] + tree[0110000] + tree[0100000]
        // tree[0110100] 对应的累加和是最右一位的1分解开以后的下标的arr累积和: arr[0110001] ~ arr[0110100]
        public int sum(int index) {
            int res = 0;
            while (index > 0) {
                res += tree[index];
                index -= index & -index;
            }
            return res;
        }

        //给arr[index] 加上d
        //最右侧位置的1提取，加上，再提前，再加上，直到 > N (N是元素个数，除去0位置)
        //arr[3] arr[011] 位置的值改变，影响tree[100] tree[1000] tree[10000]
        public void add(int index, int d) {
            while (index <= N) {
                tree[index] += d;
                index += index & -index;
            }
        }
    }

    public static class Right{
        private int[] nums;
        private int N;

        public Right(int size) {
            int N = size;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int res = 0;
            for (int i = 1; i <= index; i++) {
                res += nums[i];
            }
            return res;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 200000;

        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);

        System.out.println("test start");
        for (int i = 0; i < testTime; i++) {
            int index = (int)(Math.random() * N) + 1;
            if (Math.random() < 0.5) {
                int addVal = (int)(Math.random() * V);
                tree.add(index, addVal);
                test.add(index, addVal);
            } else {
                if (tree.sum(index) != test.sum(index)){
                    System.out.println("Oops");
                }
            }
        }
        System.out.println("test end");

    }
}
