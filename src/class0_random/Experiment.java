package class0_random;

import java.util.Arrays;

//社会实验
//开始有100个人，每个人都有100元
//在每一轮做如下的事情：
//每个人都必须拿出1元钱给除自己以外的其他人，给谁完全随机
//如果某个人在这一轮的钱数为0，那么他可以不给，但可以接收别人给的钱
//发生很多很多轮之后，这100人的社会财富分布很均匀吗？
public class Experiment {
    public static void main(String[] args) {
        System.out.println("一个社会的基尼系数是在0-1之间的小数");
        System.out.println("基尼系数为0代表所有人的财富完全一样");
        System.out.println("基尼系数为1代表有1个人掌握了全社会的财富");
        System.out.println("基尼系数越小，代表社会财富分布越均衡；越大则代表财富分布越不均衡");
        System.out.println("在2022年，世界各国平均基尼系数为0.44");
        System.out.println("目前普遍认为当基尼系数达到0.5时");
        System.out.println("就意味着社会贫富差距非常大，分布非常不均匀");
        System.out.println("社会可能会因此陷入危机，比如大量的犯罪或经历社会动荡");
        System.out.println("测试开始");

        int n = 100;
        int t = 100000;
        System.out.println("人数：" + n);
        System.out.println("轮数：" + t);

        experiment(n, t);
        System.out.println("测试结束");
    }

    //实验
    private static void experiment(int n, int t) {
        double[] wealth = new double[n];
        Arrays.fill(wealth, 100);
        boolean[] hasMoney = new boolean[n];
        //执行每一轮
        for (int i = 0; i < t; i++) {
            Arrays.fill(hasMoney, false); //初始化，再按实际有没有钱填值
            for (int j = 0; j < n; j++) {
                if (wealth[j] > 0) {
                    hasMoney[j] = true;
                }
            }
            for (int j = 0; j < n; j++) { //每个人挨个出钱
                if (hasMoney[j]) {
                    int other = j;
                    do {
                        //随机找出一个other: 0 ~ n - 1 等概率随机出现
                        other = (int)(Math.random() * n);
                    } while (other == j);
                    wealth[j]--;
                    wealth[other]++;
                }
            }
        }

        Arrays.sort(wealth);
        System.out.println("列出每个人的财富（贫穷到富有）：");
        for (int i = 0; i < n; i++) {
            System.out.print((int)wealth[i] + " ");
            if (i % 10 == 9) { //每行显示10个人
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("这个社会的基尼系数为：" + calculateGini(wealth));
    }

    //计算基尼系数
    public static double calculateGini(double[] wealth) {
        double sumOfAbsoluteDifference = 0;
        double sumOfWealth = 0;
        int n = wealth.length;

        for (int i = 0; i < n; i++) {
            sumOfWealth += wealth[i];
            for (int j = 0; j < n; j++) {
                sumOfAbsoluteDifference += Math.abs(wealth[i] - wealth[j]);
            }
        }
        //差值的绝对值之和 / (2 * 人数 * 财富总和)
        return sumOfAbsoluteDifference / (2 * n * sumOfWealth);
    }
}
