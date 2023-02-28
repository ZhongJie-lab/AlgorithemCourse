package class13_14_greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/*
输入: 正数数组costs、正数数组profits、正数K、正数M
costs[i]表示i号项目的花费
profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
K表示你只能串行的最多做k个项目
M表示你初始的资金
说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
输出：你最后获得的最大钱数。
 */
public class IPO {
    public static int findMaxCapital(int K, int W, int[] profits, int[] capital) {
        PriorityQueue<Program> minCostQueue = new PriorityQueue<>(new Comparator<Program>() { //小根堆
            @Override
            public int compare(Program o1, Program o2) {
                return o1.captial - o2.captial;
            }
        });
        PriorityQueue<Program> maxProfitQueue = new PriorityQueue<>(new Comparator<Program>() { //大根堆
            @Override
            public int compare(Program o1, Program o2) {
                return o2.profit - o1.profit;
            }
        });

        for (int i = 0; i < profits.length; i++) {
            minCostQueue.add(new Program(profits[i], capital[i]));
        }

        for (int i = 0; i < K; i++) {
            while (!minCostQueue.isEmpty() && minCostQueue.peek().captial <= W) { //根据W解锁，小根堆里能做的项目弹出来，进入大根堆
                maxProfitQueue.add(minCostQueue.poll());
            }

            if (maxProfitQueue.isEmpty()) { //手钱已经不够做K个项目了
                return W;
            }
            W += maxProfitQueue.poll().profit; //拿大根堆的堆顶，去做项目，得到利润W
        }
        return W;
    }


    public static int findMaxCapital2(int K, int W, int[] profits, int[] capital) {
        PriorityQueue<Program> minCostsHeap = new PriorityQueue<>((o1, o2) -> (o1.captial - o2.captial));
        PriorityQueue<Program> maxProfitssHeap = new PriorityQueue<>((o1, o2) -> (o2.profit - o1.profit));

        for (int i = 0; i < capital.length; i++) {
            minCostsHeap.add(new Program(profits[i], capital[i]));
        }

        for (int i = 0; i < K; i++) {
            while (!minCostsHeap.isEmpty() && minCostsHeap.peek().captial <= W) {
                maxProfitssHeap.add(minCostsHeap.poll());
            }

            if (maxProfitssHeap.isEmpty()) {
                return W;
            }
            W += maxProfitssHeap.poll().profit;
        }

        return W;
    }

    static class Program {
        int profit;
        int captial;

        public Program(int profit, int captial) {
            this.profit = profit;
            this.captial = captial;
        }
    }
}
