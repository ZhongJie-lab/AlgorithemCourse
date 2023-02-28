package class11_12_binaryTree;

public class PaperFolding {
    public static void printAllFolds(int N) {
        process(1, N, true);
    }

    private static void process(int i, int N, boolean down) {
        if (i > N) return;

        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        printAllFolds(3);
    }
}
