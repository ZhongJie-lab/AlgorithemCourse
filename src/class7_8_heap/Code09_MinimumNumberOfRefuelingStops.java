package class7_8_heap;

import java.util.PriorityQueue;

// 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target 英里处。
// 沿途有加油站，每个 station[i] 代表一个加油站，
// 它位于出发位置东面 station[i][0] 英里处，并且有 station[i][1] 升汽油。
// 假设汽车油箱的容量是无限的，其中最初有 startFuel 升燃料。
// 它每行驶 1 英里就会用掉 1 升汽油。
// 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
// 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
// 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。
// 如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
// 测试链接 : https://leetcode.cn/problems/minimum-number-of-refueling-stops/
public class Code09_MinimumNumberOfRefuelingStops {
    public static int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (startFuel >= target) {
            return 0;
        }
        //大根堆，决定了所得结果一定是最低加油次数
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int to = startFuel; //当前能到的位置
        int cnt = 0;
        for (int[] station : stations) {
            int pos = station[0];
            int fuel = station[1];
            if (to < pos) { //到不了pos位置，从大根堆里拿加油站，从堆顶拿油量最大的，这些都是你能到达的
                while (!heap.isEmpty() && to < pos) {
                    to += heap.poll();
                    cnt++;
                    if (to >= target) {
                        return cnt;
                    }
                }
                if (to < pos) { //堆已空，还是没到pos，说明到不了target
                    return -1;
                }
            }
            heap.add(fuel);
        }

        //最后一个加油站已进堆（也就是最后一个加油站我是能到的），但还没有到target
        while (!heap.isEmpty()) {
            to += heap.poll();
            cnt++;
            if (to >= target) {
                return cnt;
            }
        }
        return -1;
    }
}
