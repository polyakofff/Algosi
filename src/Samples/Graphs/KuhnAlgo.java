package Samples.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KuhnAlgo {

    static Scanner in = new Scanner(System.in);
    static final int INF = (int) (1e9 + 10);
    static ArrayList<Integer>[] g;
    static boolean[] vis;
    static int[] mt;

    static boolean tryKuhn(int v) {
        if (vis[v]) return false;
        vis[v] = true;
        for (int u : g[v]) {
            if (mt[u] == -1 || tryKuhn(mt[u])) {
                mt[u] = v;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int n1 = in.nextInt();
        int n2 = in.nextInt();
        // Лучше, если n1 <= n2
        int m = in.nextInt();

        g = new ArrayList[n1];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int v1 = in.nextInt() - 1;
            int v2 = in.nextInt() - 1;
            g[v1].add(v2);
        }

        vis = new boolean[n1];
        mt = new int[n2];
        Arrays.fill(mt, -1);
        for (int i = 0; i < n1; i++) {
            if (!vis[i]) {
                Arrays.fill(vis, false);
                tryKuhn(i);
            }
        }

        for (int i = 0; i < n2; i++) {
            if (mt[i] != -1) {
                System.out.println((mt[i] + 1) + " " + (i + 1));
            }
        }
    }
}
