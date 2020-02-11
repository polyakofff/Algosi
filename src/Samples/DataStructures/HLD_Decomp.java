package Samples.DataStructures;

/*
Heavy-light decomposition
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class HLD_Decomp {

    static Scanner in = new Scanner(System.in);
    static Random rand = new Random();

    static class HeavyLight {
        ArrayList<Integer>[] g;
        SegmentTree st;
        int cnt = 0;
        int[] p, depth, next, pos, chain;

        HeavyLight(ArrayList<Integer>[] g, int[] a) {
            this.g = g;
            int n = g.length;
            p = new int[n];
            depth = new int[n];
             next = new int[n];
             Arrays.fill(next, -1);
            pos = new int[n];
            chain = new int[n];
            chain[0] = 0;
            dfs1(0, 0);
            dfs2(0, 0);

            int[] temp = new int[n];
            for (int i = 0; i < n; i++) {
                temp[pos[i]] = a[i];
            }
            st = new SegmentTree(temp);
        }

        int dfs1(int v, int from) {
            p[v] = from;
            int size = 1, maxSubSize = 0;
            for (int u : g[v]) {
                if (u != from) {
                    depth[u] = depth[v] + 1;
                    int subSize = dfs1(u, v);
                    size += subSize;
                    if (next[v] == -1 || subSize > maxSubSize) {
                        next[v] = u;
                        maxSubSize = subSize;
                    }
                }
            }
            return size;
        }

        void dfs2(int v, int from) {
            pos[v] = cnt++;
            if (next[v] != -1) {
                chain[next[v]] = chain[v];
                dfs2(next[v], v);
            }
            for (int u : g[v]) {
                if (u != from && u != next[v]) {
                    chain[u] = u;
                    dfs2(u, v);
                }
            }
        }

        int query(int v, int u) {
            int res = 0;
            while (chain[v] != chain[u]) {
                if (depth[chain[v]] < depth[chain[u]]) {
                    int temp = v; v = u; u = temp;
                }
                res += st.sumOn(pos[chain[v]], pos[v]);
                v = p[chain[v]];
            }
            if (depth[v] > depth[u]) {
                int c = v; v = u; u = c;
            }
            res += st.sumOn(pos[v], pos[u]);
            return res;
        }

        void update(int v, int u, int val) {
            while (chain[v] != chain[u]) {
                if (depth[chain[v]] < depth[chain[u]]) {
                    int temp = v; v = u; u = temp;
                }
                st.addOn(pos[chain[v]], pos[v], val);
                v = p[chain[v]];
            }
            if (depth[v] > depth[u]) {
                int c = v; v = u; u = c;
            }
            st.addOn(pos[v], pos[u], val);
        }
    }

    static class SegmentTree {
        int n;
        int[] sum;
        int[] max;
        int[] add;
        int[] color;

        SegmentTree(int[] a) {
            n = a.length;
            sum = new int[n * 4];
            max = new int[n * 4];
            add = new int[n * 4];
            color = new int[n * 4];
            Arrays.fill(color, -1);
            build(a);
        }

        void push(int v, int tl, int tm, int tr) {
            if (color[v] != -1) {
                sum[v * 2] = color[v] * (tm - tl + 1);
                max[v * 2] = color[v];
                color[v * 2] = color[v];
                sum[v * 2 + 1] = color[v] * (tr - tm);
                max[v * 2 + 1] = color[v];
                color[v * 2 + 1] = color[v];
                color[v] = -1;
            }
            sum[v * 2] += add[v] * (tm - tl + 1);
            max[v * 2] += add[v];
            add[v * 2] += add[v];
            sum[v * 2 + 1] += add[v] * (tr - tm);
            max[v * 2 + 1] += add[v];
            add[v * 2 + 1] += add[v];
            add[v] = 0;
        }

        void build(int v, int tl, int tr, int[] a) {
            if (tl == tr) {
                sum[v] = a[tl];
                max[v] = a[tl];
            } else {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm, a);
                build(v * 2 + 1, tm + 1, tr, a);
                sum[v] = sum[v * 2] + sum[v * 2 + 1];
                max[v] = Math.max(max[v * 2], max[v * 2 + 1]);
            }
        }
        void build(int[] a) {
            build(1, 0, n - 1, a);
        }

        int sumOn(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return sum[v];
            }
            int tm = (tl + tr) / 2;
            push(v, tl, tm, tr);
            if (r <= tm) {
                return sumOn(v * 2, tl, tm, l, r);
            } else if (l >= tm + 1) {
                return sumOn(v * 2 + 1, tm + 1, tr, l, r);
            } else {
                return sumOn(v * 2, tl, tm, l, tm) +
                        sumOn(v * 2 + 1, tm + 1, tr, tm + 1, r);
            }
        }
        int sumOn(int l, int r) {
            return sumOn(1, 0, n - 1, l, r);
        }

        int maxOn(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return max[v];
            }
            int tm = (tl + tr) / 2;
            push(v, tl, tm, tr);
            if (r <= tm) {
                return maxOn(v * 2, tl, tm, l, r);
            } else if (l >= tm + 1) {
                return maxOn(v * 2 + 1, tm + 1, tr, l, r);
            } else {
                return Math.max(maxOn(v * 2, tl, tm, l, tm),
                        maxOn(v * 2 + 1, tm + 1, tr, tm + 1, r));
            }
        }
        int maxOn(int l, int r) {
            return maxOn(1, 0, n - 1, l, r);
        }

        void addOn(int v, int tl, int tr, int l, int r, int val) {
            if (tl == l && tr == r) {
                sum[v] += val * (tr - tl + 1);
                max[v] += val;
                add[v] += val;
            } else {
                int tm = (tl + tr) / 2;
                push(v, tl, tm, tr);
                if (r <= tm) {
                    addOn(v * 2, tl, tm, l, r, val);
                } else if (l >= tm + 1) {
                    addOn(v * 2 + 1, tm + 1, tr, l, r, val);
                } else {
                    addOn(v * 2, tl, tm, l, tm, val);
                    addOn(v * 2 + 1, tm + 1, tr, tm + 1, r, val);
                }
                sum[v] = sum[v * 2] + sum[v * 2 + 1];
                max[v] = Math.max(max[v * 2], max[v * 2 + 1]);
            }
        }
        void addOn(int l, int r, int val) {
            addOn(1, 0, n - 1, l, r, val);
        }

        void colorOn(int v, int tl, int tr, int l, int r, int val) {
            if (tl == l && tr == r) {
                sum[v] = val * (tr - tl + 1);
                max[v] = val;
                add[v] = 0;
                color[v] = val;
            } else {
                int tm = (tl + tr) / 2;
                push(v, tl, tm, tr);
                if (r <= tm) {
                    colorOn(v * 2, tl, tm, l, r, val);
                } else if (l >= tm + 1) {
                    colorOn(v * 2 + 1, tm + 1, tr, l, r, val);
                } else {
                    colorOn(v * 2, tl, tm, l, tm, val);
                    colorOn(v * 2 + 1, tm + 1, tr, tm + 1, r, val);
                }
                sum[v] = sum[v * 2] + sum[v * 2 + 1];
                max[v] = Math.max(max[v * 2], max[v * 2 + 1]);
            }
        }
        void colorOn(int l, int r, int val) {
            colorOn(1, 0, n - 1, l, r, val);
        }
    }

    public static void main(String[] args) {
        int n = in.nextInt();

        ArrayList<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, i -> new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int v = in.nextInt() - 1;
            int u = in.nextInt() - 1;
            g[v].add(u);
            g[u].add(v);
        }

        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = in.nextInt();
        }

        HeavyLight hl = new HeavyLight(g, c);

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            if (in.nextInt() == 1) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                System.out.println(hl.query(a, b));
            } else {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                int val = in.nextInt();
                hl.update(a, b, val);
            }
        }
    }
}
