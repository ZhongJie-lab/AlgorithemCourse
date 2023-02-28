package class37_SortedMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

//https://leetcode.cn/problems/queue-reconstruction-by-height/
public class QueueReconstructionByHeight {
    public static int[][] reconstructQueue1(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        // 贪心：1) 高的排前面; 2) 如果一样高，前面比之高或相等的人数，少的排前面
        Arrays.sort(units, new UnitComparator());

        ArrayList<Unit> arrList = new ArrayList<>();
        for (Unit unit : units) {
            arrList.add(unit.k, unit);
        }
        int[][] ans = new int[N][2];
        int ind = 0;
        for (Unit unit : arrList) {
            ans[ind][0] = unit.h;
            ans[ind][1] = unit.k;
            ind++;
        }
        return ans;
    }

    public static class Unit{
        int h;
        int k;
        public Unit(int height, int greater) {
            h = height;
            k = greater;
        }
    }

    public static class UnitComparator implements Comparator<Unit> {
        @Override
        public int compare(Unit o1, Unit o2) {
            return o2.h != o1.h ? (o2.h - o1.h) : (o1.k - o2.k);
        }
    }


    public static int[][] reconstructQueue2(int[][] people) {
        //TODO
        return null;
    }
}
