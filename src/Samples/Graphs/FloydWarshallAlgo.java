package Samples.Graphs;

import java.util.Scanner;

public class FloydWarshallAlgo {

    static Scanner in = new Scanner(System.in);

    static final int INF = (int) 1e9;
    static int n, m;
    static int[][] g;
    static int[][] d;


    public static void main(String[] args) {
        n = in.nextInt();
        m = in.nextInt();

        g = new int[n][n];
        d = new int[n][n];
        for (int i = 0; i < m; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            int w = in.nextInt();
            g[v][u] = w;
            d[v][u] = w;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(d[i][j]);
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }
}
