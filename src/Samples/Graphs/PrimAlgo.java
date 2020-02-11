package Samples.Graphs;

import java.util.*;

public class PrimAlgo {

    static Scanner in;
    static final int INF = (int) (1e9 + 10);
    static ArrayList<Pair>[] g;

    static class Pair {
        int v;
        int w;

        Pair(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return v == pair.v && w == pair.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v, w);
        }
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            int w = in.nextInt();
            g[v].add(new Pair(u, w));
            g[u].add(new Pair(v, w));
        }

        // O(n^2)
        {
            long ans = 0;

            boolean[] used = new boolean[n];
            int[] mind = new int[n];
            Arrays.fill(mind, INF);
            mind[0] = 0;
            int[] from = new int[n];
            Arrays.fill(from, -1);

            for (int i = 0; i < n; i++) {
                int v = -1;
                for (int u = 0; u < n; u++) {
                    if (!used[u] && (v == -1 || mind[u] < mind[v])) {
                        v = u;
                    }
                }
                if (mind[v] == INF) {
                    System.out.println("No MST");
                    return;
                }
                if (from[v] != -1) {
                    ans += mind[v];
                    System.out.println((from[v] + 1) + " " + (v + 1));
                }
                used[v] = true;
                for (Pair p : g[v]) {
                    if (!used[p.v] && p.w < mind[p.v]) {
                        mind[p.v] = p.w;
                        from[p.v] = v;
                    }
                }
            }

            System.out.println(ans);
        }


        // O(m*log(n))
        {
            long ans = 0;

            boolean[] used = new boolean[n];
            int[] mind = new int[n];
            Arrays.fill(mind, INF);
            mind[0] = 0;
            int[] from = new int[n];
            Arrays.fill(from, -1);
            TreeSet<Pair> ts = new TreeSet<>((o1, o2) -> {
                if (o1.w == o2.w) {
                    return Integer.compare(o1.v, o2.v);
                }
                return Integer.compare(o1.w, o2.w);
            });
            ts.add(new Pair(0, 0));

            for (int i = 0; i < n; i++) {
                if (ts.isEmpty()) {
                    System.out.println("No MST");
                    return;
                }
                Pair pf = ts.pollFirst();
                int v = pf.v;
                int w = pf.w;
                if (from[v] != -1) {
                    ans += w;
                    System.out.println((from[v] + 1) + " " + (v + 1));
                }
                used[v] = true;
                for (Pair p : g[v]) {
                    if (!used[p.v] && p.w < mind[p.v]) {
                        ts.remove(new Pair(p.v, mind[p.v]));
                        mind[p.v] = p.w;
                        from[p.v] = v;
                        ts.add(new Pair(p.v, mind[p.v]));
                    }
                }
            }

            System.out.println(ans);
        }
    }
}
