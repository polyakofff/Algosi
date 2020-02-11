package Samples.Graphs;

/*
Least Common Ancestor
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LCA {

    static Scanner in = new Scanner(System.in);
    static final int LOGN = 20;
    static int n, m;
    static ArrayList<Integer>[] g;
    static int[] tin, tout;
    static int timer = 0;
    static int[][] p;

    static void dfs(int v, int from) {
        tin[v] = ++timer;
        p[v][0] = from;
        for (int i = 1; i < LOGN; i++) {
            p[v][i] = p[p[v][i - 1]][i - 1];
        }
        for (int u : g[v]) {
            if (u != from) {
                dfs(u, v);
            }
        }
        tout[v] = ++timer;
    }

    static boolean isAnc(int v, int u) {
        return tin[v] < tin[u] && tout[u] < tout[v];
    }

    static int lca(int v, int u) {
        if (v == u) return v;
        if (isAnc(v, u)) return v;
        if (isAnc(u, v)) return u;
        for (int i = LOGN - 1; i >= 0; i--) {
            if (!isAnc(p[v][i], u)) {
                v = p[v][i];
            }
        }
        return p[v][0];
    }

    public static void main(String[] args) {
        n = in.nextInt();

        g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            g[v].add(u);
            g[u].add(v);
        }

        tin = new int[n];
        tout = new int[n];
        p = new int[n][LOGN];
        dfs(0, 0);

        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            System.out.println(lca(v, u) + 1);
        }
    }
}
