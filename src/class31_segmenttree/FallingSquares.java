package class31_segmenttree;

//https://leetcode.cn/problems/falling-squares/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class FallingSquares {

    public static List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size(); //映射以后，一共N个点

        SegmentTree segmentTree = new SegmentTree(N);
        List<Integer> ans = new ArrayList<>();
        int max = 0;

        for (int[] pos : positions) {
            //获得虚拟坐标的左右边界
            int L = map.get(pos[0]);
            int R = map.get(pos[0] + pos[1] - 1);
            //pos[1] 当前落下方块的高度
            //segmentTree.query(L, R, 1, N, 1) ， 区间[L, R]上最高的高度
            //两者相加得到[L, R]的新的最高高度
            int maxHeight = segmentTree.query(L, R, 1, N, 1) + pos[1];
            max = Math.max(maxHeight, max);
            ans.add(max);
            segmentTree.update(L, R, maxHeight, 1, N, 1);
        }
        return ans;
    }

    //坐标压缩 - M个position, 2*M个坐标点，去重，排序，映射成虚拟坐标
    private static HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> posSet = new TreeSet<>();
        for (int[] pos : positions) {
            posSet.add(pos[0]);
            posSet.add(pos[0] + pos[1] - 1);
        }
        //坐标0弃而不用
        int count = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int ind : posSet) {
            map.put(ind, ++count);
        }
        return map;
    }

    //更新+查询max的线段树
    public static class SegmentTree {
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size) { //没有原数组，因为一开始原数组上的值都是0
            int N = size + 1;
            max = new int[N << 2];  //max[i]： 它所表示的范围[L。。。R]上的最大高度
            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            //任务全包
            if (L <= l && R >= r) {
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                return;
            }
            //任务没有全包，下发任务
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return max[l];
            }

            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int leftMax = 0, rigthMax = 0;
            if (L <= mid) {
                leftMax = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                rigthMax = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(leftMax, rigthMax);
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];

                update[rt] = false;
            }
        }
    }

    public static int[] height;
    public static List<Integer> fallingSquares2(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size(); //新坐标点个数
        height = new int[N + 1]; //第一个坐标0保留不用，所以长度是N + 1

        int max = 0;
        List<Integer> ans = new ArrayList<>();

        for (int[] pos : positions) {
            int L = map.get(pos[0]);
            int R = map.get(pos[0] + pos[1] - 1);
            int h = query(L, R) + pos[1]; //当前[L..R]范围上的最大高度 + 我的高度 = 方块落下以后[L..R]范围上的最大高度
            update(L, R, h);
            max = Math.max(max, h);
            ans.add(max);
        }
        return ans;
    }

    private static void update(int L, int R, int h) {
        for (int i = L; i <= R; i++) {
            height[i] = Math.max(h, height[i]);
        }
    }

    //查找[L...R]范围上的最大值
    private static int query(int L, int R) {
        int max = 0;
        for (int i = L; i <= R; i++) {
            max = Math.max(max, height[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] positions = {
                {1, 2},
                {2, 3},
                {6, 1}
        };

        List<Integer> ans1 = fallingSquares(positions);
        List<Integer> ans2 = fallingSquares2(positions);
        ans1.forEach(System.out::println);
        System.out.println();
        ans2.forEach(System.out::println);

    }
}
