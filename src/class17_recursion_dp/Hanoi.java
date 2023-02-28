package class17_recursion_dp;

public class Hanoi {
    public static void hanoi(int n) {
        if (n > 0) {
            process(n, "left", "right", "mid");
        }
    }

    //抽象成from(left) to(right) other(mid)
    private static void process(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            process(n - 1, from, other, to);
            System.out.println("Move " + n + " from " + from + " to " + to);
            process(n - 1, other, to, from);
        }
    }
}
