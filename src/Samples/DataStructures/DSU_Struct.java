package Samples.DataStructures;

public class DSU_Struct {

    static class DSU {
        int[] par;
        int[] rank;

        DSU(int n) {
            par = new int[n];
            rank = new int[n];
            for (int v = 0; v < n; v++) {
                par[v] = v;
                rank[v] = 0;
            }
        }

        int findSet(int v) {
            if (par[v] == v) return v;
            return par[v] = findSet(par[v]);
        }

        void uniteSets(int v, int u) {
            v = findSet(v);
            u = findSet(u);
            if (v != u) {
                if (rank[v] > rank[u]) {
                    par[u] = v;
                } else if (rank[v] < rank[u]) {
                    par[v] = u;
                } else {
                    par[u] = v;
                    rank[v]++;
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
