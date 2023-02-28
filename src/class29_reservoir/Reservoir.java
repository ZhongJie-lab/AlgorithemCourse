package class29_reservoir;

/*
蓄水池算法

假设有一个源源吐出不同球的机器，
只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
 */

public class Reservoir {
    public static class RandomBox {
        private int[] bag;
        private int N;      //袋子的容量
        private int count;  //突出的球的数量

        public RandomBox(int capacity) {
            bag = new int[capacity];
            N = capacity;
            count = 0;
        }

        //返回值范围落在 1 到 max
        private int random(int max) {
            return (int)(Math.random() * max) + 1;
        }

        public void add(int num) {
            count++;
            if (count <= N) {
                bag[count - 1] =num;
            } else {
                if (random(count) <= N) { //第count个球，以count/N 的概率进入袋子，同时踢掉袋子中的某个球
                    int bagi = random(N) - 1;
                    bag[bagi] = num;
                } //else 直接扔掉
            }
        }

        public int[] choices() {
            int ans[] = new int[N];
            for (int i = 0; i < N; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int capacity = 10;
        int all = 100; //代表所有球
        int[] counts = new int[all + 1];

        for (int i = 0; i < testTimes; i++) {
            RandomBox randomBox = new RandomBox(capacity);
            for (int num = 1; num <= all; num++) {
                randomBox.add(num);
            }
            int[] ans = randomBox.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 1; i <= all; i++) {
            System.out.println(i + " times: " + counts[i]);
        }
    }
}
