package Samples.DataStructures;

import java.util.ArrayList;

public class PersistentSegmentTree_Struct {

    static class PersistentSegmentTree {
        class Node {
            int sum;
            Node l, r;

            Node() {
            }
        }

        int n;
        ArrayList<Node> versions;

        PersistentSegmentTree(int[] a) {
            n = a.length;
            versions = new ArrayList<>();
            versions.add(new Node());
            build(versions.get(0), a);
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
        void build(Node t, int[] a) {
            build(t, 0, n - 1, a);
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
        int sumOn(int version, int l, int r) {
            return sumOn(versions.get(version), 0, n - 1, l, r);
        }

        void update(Node t, Node prev, int tl, int tr, int i, int val) {
            if (i < tl || i > tr) return;
            if (tl == tr) {
                t.sum = val;
            } else {
                int tm = (tl + tr) / 2;
                if (i <= tm) {
                    t.l = new Node();
                    t.r = prev.r;
                    update(t.l, prev.l, tl, tm, i, val);
                } else {
                    t.r = new Node();
                    t.l = prev.l;
                    update(t.r, prev.r, tm + 1, tr, i, val);
                }
                t.sum = t.l.sum + t.r.sum;
            }
        }
        void update(int i, int val) {
            versions.add(new Node());
            update(versions.get(versions.size() - 1), versions.get(versions.size() - 2), 0, n - 1, i, val);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
        PersistentSegmentTree st = new PersistentSegmentTree(a);

        System.out.println(st.sumOn(0, 0, 7));

        st.update(0, 10);

        System.out.println(st.sumOn(0, 0, 2));
        System.out.println(st.sumOn(1, 0, 2));
    }
}
