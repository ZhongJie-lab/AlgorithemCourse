package class37_SortedMap;

import java.util.HashSet;

// https://leetcode-cn.com/problems/count-of-range-sum/
//思路：求以i位置结尾的子数组的累加和在[lower, upper]范围上的有几个？-> [0 .. i]的累加和是x，i之前的前缀和数组在[x - upper, x - lower]的个数有几个
public class CountOfRangeSum {
    //O(NlogN)
    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) return 0;

        int ans = 0;
        long sum = 0;
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        treeSet.add(0);
        for (int i = 0; i < nums.length; i++) {
            //当前的前缀和
            sum += nums[i];
//            System.out.println("sum " + sum);
            //求有序表中，落在[sum - upper, sum - lower]范围上的数字个数
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            //加入前缀和到有序表
            treeSet.add(sum);
        }
        return ans;
    }

    //加入数字（前缀和），不去重，可以接受重复数字
    //查询，[min, max] 有几个数，等同于 求 < min 的数n1, <= max 的数n2, 就是n2 - n1
    public static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>();

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }

            long leftSize = cur.l != null ? cur.l.size : 0;
            long leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            long leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            long rightSize = cur.r != null ? cur.r.size : 0;
            long rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            long rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

            if (leftLeftSize > rightSize) {
                //一次右旋
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                //一次左旋，一次右旋
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                //一次左旋
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                //一次右旋，一次左旋
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode leftRotate(SBTNode cur) {
            //在旋转之前，先拿住cur节点上的key的加入的个数
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            //修正all
            right.all = cur.all; //整棵子树的all是不变的
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same; //cur的右树改变了，所以要重算
            return right;
        }

        private SBTNode rightRotate(SBTNode cur) {
            long same = cur.all - (cur.l != null ? cur.l.all : 0) - (cur.r != null ? cur.r.all : 0);
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;

            left.all = cur.all;
            cur.all = (cur.l != null ? cur.l.all : 0) + (cur.r != null ? cur.r.all : 0) + same;
            return left;
        }

        private SBTNode add(SBTNode cur, long sum, boolean isExist) {
            if (cur == null) {
                return new SBTNode(sum);
            }
            cur.all++;
            if (cur.key == sum) { //当前节点的key和要加入的数相等，返回当前节点
                return cur;
            } else { //当前节点的key和要加入的数不等，往左滑或往右滑
                //如果sum没被加入过，需要新加节点
                if (!isExist) {
                    cur.size++;
                }
                if (sum < cur.key) {
                    cur.l = add(cur.l, sum, isExist);
                } else {
                    cur.r = add(cur.r, sum, isExist);
                }
                return maintain(cur);
            }
        }

        public void add(long sum) {
            boolean isExist = set.contains(sum);
            root = add(root, sum, isExist);
            set.add(sum);
        }

        //小于key的数字个数
        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {
                    return ans + (cur.l != null ? cur.l.all : 0);
                } else if (key < cur.key) {
                    cur = cur.l;
                } else { //我比当前节点key大，说明左树和cur都比我小，收集答案，并且，继续探索右树上比我小的key数量
                    ans += cur.all - (cur.r != null ? cur.r.all : 0);
                    cur = cur.r;
                }
            }
            return ans;
        }
    }

    public static class SBTNode {
        public long key;
        public SBTNode l;
        public SBTNode r;
        long size; //这棵树上的节点个数
        long all; //从这棵树上加入过的数字总数

        public SBTNode(long k) {
            key = k;
            size = 1;
            all = 1;
        }
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }


    public static void main(String[] args) {
        int len = 200;
        int varible = 50;
        System.out.println("begin ...");
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum(test, lower, upper);
            int ans2 = class5_6_10_sort.mergesort.CountOfRangeSum.countRangeSum(test, lower, upper);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("end ...");
//        int[] nums = {-2, 5, -1};
//        int lower = -2;
//        int upper = 2;
//        System.out.println(countRangeSum(nums, lower, upper));
    }
}
