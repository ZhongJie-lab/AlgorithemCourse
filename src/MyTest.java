import java.util.Arrays;

public class MyTest {
    //@ - 墙； # - 发射器
    static int[] help = new int[3];
    public static int[] solution(char[][] map, int x, int y) {
        int N = map.length;
        int M = map[0].length;
        int[][] signal = new int[N][M];
        help = new int[] {x, y, 0};

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '#') {
                    for (int k = i + 1; k < N; k++) {
                        if (map[k][j] != '@') {
                            if (map[k][j] != '#') signal[k][j]++;
                        } else {
                            break;
                        }
                    }
                    for (int k = i - 1; k >= 0; k--) {
                        if (map[k][j] != '@') {
                            if (map[k][j] != '#') signal[k][j]++;
                        } else {
                            break;
                        }
                    }
                    for (int k = j + 1; k < M; k++) {
                        if (map[i][k] != '@') {
                            if (map[i][k] != '#') signal[i][k]++;
                        } else {
                            break;
                        }
                    }
                    for (int k = j - 1; k >= 0; k--) {
                        if (map[i][k] != '@') {
                            if (map[i][k] != '#') signal[i][k]++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        process(map, signal, x, y);

        return new int[]{help[0], help[1]};
    }

    private static void process(char[][] map, int[][] signal, int i, int j) {
        if (i < 0 || i > map.length || j < 0 || j > map[0].length || map[i][j] != '.')  {
            return;
        }
        if (signal[i][j] > help[3]) {
            help = new int[] {i, j, signal[i][j]};
        }
        process(map, signal, i - 1, j);
        process(map, signal, i + 1, j);
        process(map, signal, i, j - 1);
        process(map, signal, i, j + 1);
    }

    public static void main(String[] args) {

    }
}
