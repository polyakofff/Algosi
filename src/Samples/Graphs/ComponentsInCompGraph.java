package Samples.Graphs;

import java.util.*;

/**
 * Компоненты связности в дополняющем графе.
 * (Граф, где есть все ребра кроме m заданных)
 */

public class ComponentsInCompGraph {

    static Scanner in = new Scanner(System.in);

    static class Edge {
        int v, u;

        Edge(int v, int u) {
            this.v = Math.min(v, u);
            this.u = Math.max(v, u);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return v == edge.v &&
                    u == edge.u;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v, u);
        }
    }

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();

        HashSet<Edge> g = new HashSet<>();
        for (int i = 0; i < m; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            g.add(new Edge(v, u));
        }

        HashSet<Integer> s = new HashSet<>();
        for (int i = 0; i < n; i++) {
            s.add(i);
        }

        int cnt = 0;
        LinkedList<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (!s.contains(i)) continue;
            cnt++;
            q.add(i);
            s.remove(i);
            while (!q.isEmpty()) {
                int v = q.poll();
                for (Iterator<Integer> it = s.iterator(); it.hasNext();) {
                    int u = it.next();
                    if (!g.contains(new Edge(v, u))) {
                        q.add(u);
                        it.remove();
                    }
                }
            }
        }

        System.out.println(cnt);
    }
}
