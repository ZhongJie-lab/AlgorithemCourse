package class11_12_binaryTree.dfs;

//派对的最大快乐值
//每个员工都符合Employee类的描述，整个公司的结构可以看作是一棵标准的，没有环的多叉树。树的头节点是老板，除老板之外每个员工都有唯一的直接上级。
//叶节点是没有任何下属的基层员工，除基层员工外，每个员工都有一个或多个直接下属。
//这个公司要办party，你决定哪些员工来，哪些员工不来：
//1. 如果某个员工来了，那么这个圆滚滚的所有直接下属都不能来
//2. 排队的整体快乐值是所有到场员工的快乐值的累加

import java.util.ArrayList;
import java.util.List;

public class MaxHappy {
    public static int maxHappy(Employee boss) {
        Info bossInfo = process(boss);
        return Math.max(bossInfo.join, bossInfo.notJoin);
    }

    public static Info process(Employee head) {
        if (head == null) {
            return new Info(0, 0);
        }

        int join = 0;
        int notJoin = 0;
        for (Employee employee : head.subordinates) {
            Info empInfo = process(employee);
            notJoin += Math.max(empInfo.join, empInfo.notJoin);
            join += empInfo.notJoin;
        }
        return new Info(join, notJoin);
    }

    public static class Info {
        int join;
        int notJoin;

        public Info(int yes, int no) {
            join = yes;
            notJoin = no;
        }
    }

    public static class Employee {
        int happy;
        List<Employee> subordinates;

        public Employee(int happy) {
            this.happy = happy;
            this.subordinates = new ArrayList<>();
        }
    }
}
