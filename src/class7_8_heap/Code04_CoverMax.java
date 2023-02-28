package class7_8_heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//最大线段重合问题（用堆实现）
//给定很多线段，每个线段都有两个数[start, end]，
//表示线段开始位置和结束位置，左右都是闭区间
//规定：
//1）线段的开始和结束位置一定都是整数值
//2）线段重合区域的长度必须>=1
//返回线段最多重合区域中，包含了几条线段
public class Code04_CoverMax {
    //时间复杂度 O(N*logN)
    public static int maxCover(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line (m[i][0], m[i][1]);
        }
        Arrays.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return o1.start - o2.start;
            }
        });
        //小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        //每个线段的尾部放入小根堆，小根堆有几个数字就是这个线段的答案
        //每次放入线段尾部时，把堆内小于等于当前线段的起始的数都弹出（一定不重合）
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end); //自己也要加到堆里
            //此时的heap size是当前线段的答案，和max比较
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static int maxCover2(int[][] m) {
        Arrays.sort(m, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int[] line : m) {
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            max = Math.max(heap.size(), max);
        }
        return max;
    }

    public static int maxCover22(int[][] m) {
        if (m == null || m.length == 0) return 0;

        Arrays.sort(m, (a, b) -> (a[0] - b[0]));

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;

        for (int i = 0; i < m.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= m[i][0]) {
                heap.poll();
            }
            heap.add(m[i][1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }

    // for test
    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maxCover(new int[][]{{1, 5}, {5, 9}, {10, 15}}));
//        Line l1 = new Line(4, 9);
//        Line l2 = new Line(1, 4);
//        Line l3 = new Line(7, 15);
//        Line l4 = new Line(2, 4);
//        Line l5 = new Line(4, 6);
//        Line l6 = new Line(3, 7);
//
//        // 底层堆结构，heap
//        PriorityQueue<Line> heap = new PriorityQueue<>((a, b) -> (a.start - b.start));
//        heap.add(l1);
//        heap.add(l2);
//        heap.add(l3);
//        heap.add(l4);
//        heap.add(l5);
//        heap.add(l6);
//
//        while (!heap.isEmpty()) {
//            Line cur = heap.poll();
//            System.out.println(cur.start + "," + cur.end);
//        }
//
//        System.out.println("test begin");
//        int N = 100;
//        int L = 0;
//        int R = 200;
//        int testTimes = 200000;
//        for (int i = 0; i < testTimes; i++) {
//            int[][] lines = generateLines(N, L, R);
//            int ans1 = maxCover(lines);
//            int ans2 = maxCover2(lines);
//            int ans0 = maxCover0(lines);
//            if (ans0 != ans2 || ans0 != ans1) {
//                System.out.println("Oops!");
//            }
//        }
//        System.out.println("test end");
    }

    private static int maxCover0(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }
}
