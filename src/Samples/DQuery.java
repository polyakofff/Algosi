package Samples;

/*
Количество различных чисел на отрезке
 */

import java.util.Arrays;
import java.util.Scanner;

public class DQuery {

    static Scanner in = new Scanner(System.in);
    static final int N = (int) (1e6);

    static class SegmentTree {
        class Node {
            int sum;
            Node l, r;

            Node() {
            }
        }

        int n;
        Node[] ts;

        SegmentTree(int[] a) {
            n = a.length;
            ts = new Node[n];
            int[] b = new int[n];
            int[] next = new int[n];
            int[] pos = new int[N + 1];
            Arrays.fill(pos, -1);
            for (int i = n - 1; i >= 0; i--) {
                b[i] = 1;
                if (pos[a[i]] != -1) {
                    b[pos[a[i]]] = 0;
                }
                next[i] = pos[a[i]];
                pos[a[i]] = i;
            }

            ts[0] = new Node();
            build(ts[0], 0, n - 1, b);
            for (int i = 1; i < n; i++) {
                Node t1 = new Node();
                update(t1, ts[i - 1], 0, n - 1, i - 1, 0);
                if (next[i - 1] != -1) {
                    Node t2 = new Node();
                    update(t2, t1, 0, n - 1, next[i - 1], 1);
                    ts[i] = t2;
                } else {
                    ts[i] = t1;
                }
            }
        }

        void build(Node t, int tl, int tr, int[] a) {
            if (tl == tr) {
                t.sum = a[tl];
            } else {
                t.l = new Node();
                t.r = new Node();
                int tm = (tl + tr) / 2;
                build(t.l, tl, tm, a);
                build(t.r, tm + 1, tr, a);
                t.sum = t.l.sum + t.r.sum;
            }
        }

        void update(Node t, Node t1, int tl, int tr, int i, int val) {
            if (i < tl || i > tr) return;
            if (tl == tr) {
                t.sum = val;
            } else {
                int tm = (tl + tr) / 2;
                if (i <= tm) {
                    t.l = new Node();
                    t.r = t1.r;
                    update(t.l, t1.l, tl, tm, i, val);
                } else {
                    t.r = new Node();
                    t.l = t1.l;
                    update(t.r, t1.r, tm + 1, tr, i, val);
                }
                t.sum = t.l.sum + t.r.sum;
            }
        }

        int sumOn(Node t, int tl, int tr, int l, int r) {
            if (l > r) return 0;
            if (l <= tl && tr <= r) {
                return t.sum;
            }
            int tm = (tl + tr) / 2;
            return sumOn(t.l, tl, tm, l, Math.min(r, tm)) +
                    sumOn(t.r, tm + 1, tr, Math.max(l, tm + 1), r);
        }
        int countOn(int l, int r) {
            return sumOn(ts[l], 0, n - 1, l, r);
        }
    }

    public static void main(String[] args) {
        int n = in.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        SegmentTree st = new SegmentTree(a);

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int l = in.nextInt() - 1;
            int r = in.nextInt() - 1;
            System.out.println(st.countOn(l, r));
        }
    }
}
