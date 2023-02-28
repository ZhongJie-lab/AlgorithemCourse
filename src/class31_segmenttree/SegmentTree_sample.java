package class31_segmenttree;

public class SegmentTree_sample {
    public static class SegmentTree {
        private int[] arr;
        private int[] sum; //模拟线段树维护区间和
        private int[] lazy; //累加和懒标记
        private int[] change; //更新的值
        private boolean[] update; //更新的懒标记
        private int MAXN;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN]; //arr[0]不用，从arr[1]开始
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }

            sum = new int[MAXN << 2]; //4 * MAXN
            lazy = new int[MAXN << 2];
            change = new int[MAXN << 2];
            update = new boolean[MAXN << 2];
        }


        //初始化阶段，在arr[l..r]范围上， build好sum
        //rt：这个范围在sum中的下标
        public void build (int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1); //build左树
            build(mid + 1, r, rt << 1 | 1); //build右树
            pushUp(rt);
        }

        //L R C 任务 [L...R]范围上每个数加C
        //当前来到sum的rt格子，rt表示的是l - r范围
        // 时间复杂度：整棵树，走左边一条界，右边一条界，中间都懒住了，所以时间复杂度走2遍树的高度 O(logN)
        public void add(int L, int R, int C, int l, int r, int rt) {
            //任务把此时的范围l..r全包了，可以懒更新
            if (L <= l && r <= R) {
                sum[rt] += (r - l + 1) * C;
                lazy[rt] += C; //任务不往下发了，正是lazy决定了时间复杂度不是O(N)而是O(logN)
                return;
            }

            //任务没有把你全包，无法懒更新，要往下发任务
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            //左孩子是否需要接任务
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            //右孩子是否需要接任务
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            //左右孩子做完任务后，我更新我的sum信息
            pushUp(rt);
        }

        //L..R 所有值变C
        //rt表示l - r范围
        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && R >= r) {
                update[rt] = true;
                change[rt] = C;
                sum[rt] = (r - l + 1) * C;
                lazy[rt] = 0;
                return;
            }
            //当前任务没有全包，躲不掉，要往下发
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) { //左孩子要接任务
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) { //右孩子要接任务
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        //L...R的累加和是多少？
        public long query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, l - mid + 1, r - mid);
            int ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

        //rt，ln表示左子树元素个数，rn表示右子树元素个数
        // pushDown只发一层
        // 发送缓存任务到下一级
        private void pushDown(int rt, int ln, int rn) {
            //如果既有update信息，又有lazy信息，一定是因为更新任务后面发生了累加任务，因为，假设累加任务在前，后面的更新任务会将其清空
            //所以，先处理更新信息，再处理累加信息
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                sum[rt << 1] = sum[rt] * ln;
                sum[rt << 1 | 1] = sum[rt] * rn;
                update[rt] = false;
            }
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += ln * lazy[rt];
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += rn * lazy[rt];
                lazy[rt] = 0;
            }
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }
    }

    public static class Right {
        public int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }

        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }

        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }

    }

    public static int[] generateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 500;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;

        for (int i = 0; i < testTimes; i++) {
            int[] origin = generateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right right = new Right(origin);

            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int)(Math.random() * N) + 1;
                int num2 = (int)(Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int)(Math.random() * max) - (int)(Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    right.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    right.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int)(Math.random() * N) + 1;
                int num2 = (int)(Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = seg.query(L, R, S, N, root);
                if (ans1 != ans2) {
                    printArr(origin);
                    printArr(seg.sum);
                    printArr(seg.change);
                    System.out.println("L " + L);
                    System.out.println("R " + R);
                    return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4, 5};
        SegmentTree seg= new SegmentTree(origin);
        int S = 1;
        int N = origin.length;

        int root = 1; //整棵树的头节点位置
        int L = 2;
        int R = 5;
        int C = 4;

        //区间生成，必须在[S, N]整个范围上build
        seg.build(S, N, root);
        //区间修改，可以改变L R C的值，其他值不可变
        seg.add(L, R, C, S, N, root);
        printArr(seg.sum);
        seg.update(L, R, C, S, N, root);
        printArr(seg.sum);
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("对数器测试结束 ： " + (test() ? "pass" : "failed"));
    }

    private static void printArr(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
