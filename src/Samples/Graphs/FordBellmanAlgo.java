package Samples.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class FordBellmanAlgo {

    static Scanner in;

    static class Edge {
        int a, b, cost;

        Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    static int n, m, from;
    static ArrayList<Edge> edges;
    static int[] d;
    static int[] p;
    static int inf = Integer.MAX_VALUE;

    public static void main(String[] args) {
        in = new Scanner(System.in);

        n = in.nextInt();
        m = in.nextInt();

        edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int cost = in.nextInt();
            edges.add(new Edge(a, b, cost));
        }

        from = in.nextInt();

        d = new int[n + 1];
        Arrays.fill(d, inf);
        d[from] = 0;

        p = new int[n + 1];
        Arrays.fill(p, -1);
        p[from] = from;

        int v = -1;
        for (int i = 0; i < n; i++) {
            v = -1;
            for (Edge e : edges) {
                if (d[e.a] < inf) {
                    if (d[e.a] + e.cost < d[e.b]) {
                        d[e.b] = d[e.a] + e.cost;
                        p[e.b] = e.a;
                        v = e.b;
                    }
                }
            }
//            if (x == -1) break;
        }

        if (v == -1) {
            System.out.println("No negative cycle!");
            for (int u = 1; u <= n; u++) {
                System.out.printf("%d -> %d (%d)\n", from, u, d[u]);
            }
        } else {
            System.out.println("Negative cycle!");

            for (int i = 0; i < n; i++) {
                v = p[v];
            }

            ArrayList<Integer> cycle = new ArrayList<>();
            for (int u = v; ; u = p[u]) {
                cycle.add(u);
                if (u == v && cycle.size() > 1) {
                    break;
                }
            }
            Collections.reverse(cycle);

            for (int u : cycle) {
                System.out.print(u + " ");
            }
            System.out.println();
        }
    }
}
