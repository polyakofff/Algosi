package Samples.DataStructures;

import java.util.Arrays;

public class SegmentTree_Struct {

    static final int INF = (int) (1e9 + 10);

    static class SegmentTree {
        int n;
        int[] sum, max, add, color;

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
            if (add[v] != 0) {
                sum[v * 2] += add[v] * (tm - tl + 1);
                max[v * 2] += add[v];
                add[v * 2] += add[v];
                sum[v * 2 + 1] += add[v] * (tr - tm);
                max[v * 2 + 1] += add[v];
                add[v * 2 + 1] += add[v];
                add[v] = 0;
            }
        }
        void pull(int v) {
            sum[v] = sum[v * 2] + sum[v * 2 + 1];
            max[v] = Math.max(max[v * 2], max[v * 2 + 1]);
        }

        void build(int v, int tl, int tr, int[] a) {
            if (tl == tr) {
                sum[v] = a[tl];
                max[v] = a[tl];
            } else {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm, a);
                build(v * 2 + 1, tm + 1, tr, a);
                pull(v);
            }
        }
        void build(int[] a) {
            build(1, 0, n - 1, a);
        }

        int sumOn(int v, int tl, int tr, int l, int r) {
            if (l > r) return 0;
            if (l <= tl && tr <= r) return sum[v];
            int tm = (tl + tr) / 2;
            push(v, tl, tm, tr);
            return sumOn(v * 2, tl, tm, l, Math.min(r, tm)) +
                    sumOn(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        }
        int sumOn(int l, int r) {
            return sumOn(1, 0, n - 1, l, r);
        }

        int maxOn(int v, int tl, int tr, int l, int r) {
            if (l > r) return -INF;
            if (l <= tl && tr <= r) return max[v];
            int tm = (tl + tr) / 2;
            push(v, tl, tm, tr);
            return Math.max(maxOn(v * 2, tl, tm, l, Math.min(r, tm)),
                    maxOn(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r));
        }
        int maxOn(int l, int r) {
            return maxOn(1, 0, n - 1, l, r);
        }

        void addOn(int v, int tl, int tr, int l, int r, int val) {
            if (l > r) return;
            if (l <= tl && tr <= r) {
                sum[v] += val * (tr - tl + 1);
                max[v] += val;
                add[v] += val;
            } else {
                int tm = (tl + tr) / 2;
                push(v, tl, tm, tr);
                addOn(v * 2, tl, tm, l, Math.min(r, tm), val);
                addOn(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, val);
                pull(v);
            }
        }
        void addOn(int l, int r, int val) {
            addOn(1, 0, n - 1, l, r, val);
        }

        void colorOn(int v, int tl, int tr, int l, int r, int val) {
            if (l > r) return;
            if (l <= tl && tr <= r) {
                sum[v] = val * (tr - tl + 1);
                max[v] = val;
                add[v] = 0;
                color[v] = val;
            } else {
                int tm = (tl + tr) / 2;
                push(v, tl, tm, tr);
                colorOn(v * 2, tl, tm, l, Math.min(r, tm), val);
                colorOn(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, val);
                pull(v);
            }
        }
        void colorOn(int l, int r, int val) {
            colorOn(1, 0, n - 1, l, r, val);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[8];
    }
}
