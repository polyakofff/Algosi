package Samples.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class TopologicalSort {

    static Scanner in = new Scanner(System.in);
    static int n, m;
    static ArrayList<Integer>[] g;
    static boolean[] vis;
    static ArrayList<Integer> ans;

    static void dfs(int v) {
        vis[v] = true;
        for (int u : g[v]) {
            if (!vis[u]) {
                dfs(u);
            }
        }
        ans.add(v);
    }

    static ArrayList<Integer> topologicalSort() {
        vis = new boolean[n];
        ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                dfs(i);
            }
        }
        Collections.reverse(ans);
        return ans;
    }

    public static void main(String[] args) {
        n = in.nextInt();
        m = in.nextInt();

        g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            g[v].add(u);
        }

        topologicalSort();

        for (int a : ans) {
            System.out.print((a + 1) + " ");
        }
        System.out.println();
    }
}
