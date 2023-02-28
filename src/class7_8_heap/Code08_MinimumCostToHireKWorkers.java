package class7_8_heap;

import java.util.Arrays;
import java.util.PriorityQueue;

// 有 n 名工人。 给定两个数组 quality 和 wage ，
// 其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
// 现在我们想雇佣 k 名工人组成一个工资组。在雇佣 一组 k 名工人时，
// 我们必须按照下述规则向他们支付工资：
// 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
// 工资组中的每名工人至少应当得到他们的最低期望工资。
// 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额
// 测试链接 : https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/
public class Code08_MinimumCostToHireKWorkers {
    public static double minCost(int[] quality, int[] wage, int k) {
        //垃圾指数从低到高排列，不违反规则的话只能以垃圾指数最高的人为标准去选人
        // rubbishDegree = w/q, max(rubbishDegree) * (q1 + q2 + q3)
        //只选k个人，最低期望薪资。从小到大遍历所有垃圾指数，每次选择quality前k小的员工
        int N = quality.length;
        Employee[] employees = new Employee[N];
        for (int i = 0; i < N; i++) {
            employees[i] = new Employee(wage[i], quality[i]);
        }

        //按垃圾指数升序排列
        Arrays.sort(employees, (a, b) -> a.rubbishDegree <= b.rubbishDegree ? -1 : 1);
        //大根堆 - 维持quality前k小的
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        double ans = Double.MAX_VALUE;
        for (int i = 0, qSum = 0; i < N; i++) {
            //当前来到垃圾指数排序第i位的员工
            int curQuality = employees[i].quality;
            if (heap.size() < k) {
                qSum += curQuality;
                heap.add(curQuality);
                if (heap.size() == k) {
                    ans = Math.min(ans, employees[i].rubbishDegree * qSum);
                }
            } else {
                //如果当前员工的quality比堆顶更小，堆顶弹出，当前员工的quality进堆
                if (heap.peek() > employees[i].quality) {
                    qSum += curQuality - heap.poll();
                    heap.add(curQuality);
                    ans = Math.min(ans, employees[i].rubbishDegree * qSum);
                }
            }
        }
        return ans;
    }

    public static class Employee {
        public int quality;
        public double rubbishDegree;

        public Employee(int w, int q) {
            this.quality = q;
            this.rubbishDegree = (double) w / (double) q;
        }
    }
}
