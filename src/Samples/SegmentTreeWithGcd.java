package Samples;

public class SegmentTreeWithGcd {

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static class SegmentTree {
        class Node {
            int first, last, gcd, add;

            Node(int first, int last, int gcd) {
                this.first = first;
                this.last = last;
                this.gcd = gcd;
                add = 0;
            }
        }

        int n;
        Node[] t;

        SegmentTree(int[] a) {
            n = a.length;
            t = new Node[n * 4];
            build(a);
        }

        void build(int v, int tl, int tr, int[] a) {
            if (tl == tr) {
                t[v] = new Node(a[tl], a[tl], 0);
            } else {
                int tm = (tl + tr) / 2;
                build(v * 2, tl, tm, a);
                build(v * 2 + 1, tm + 1, tr, a);
                t[v] = new Node(t[v * 2].first, t[v * 2 + 1].last, gcd(t[v * 2].gcd,
                        gcd(Math.abs(t[v * 2 + 1].first - t[v * 2].last), t[v * 2 + 1].gcd)));
            }
        }
        void build(int[] a) {
            build(1, 0, n - 1, a);
        }

        void push(int v) {
            if (t[v].add != 0) {
                t[v * 2].first += t[v].add;
                t[v * 2].add += t[v].add;
                t[v * 2 + 1].first += t[v].add;
                t[v * 2 + 1].add += t[v].add;
                t[v].add = 0;
            }
        }

        int gcdOn(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return gcd(t[v].first, t[v].gcd);
            }
            int tm = (tl + tr) / 2;
            push(v);
            if (r <= tm) {
                return gcdOn(v * 2, tl, tm, l, r);
            } else if (l >= tm + 1) {
                return gcdOn(v * 2 + 1, tm + 1, tr, l, r);
            } else {
                return gcd(gcdOn(v * 2, tl, tm, l, tm),
                        gcdOn(v * 2 + 1, tm + 1, tr, tm + 1, r));
            }
        }
        int gcdOn(int l, int r) {
            return gcdOn(1, 0, n - 1, l, r);
        }

        void addOn(int v, int tl, int tr, int l, int r, int val) {
            if (tl == l && tr == r) {
                t[v].first += val;
                t[v].add += val;
            } else {
                int tm = (tl + tr) / 2;
                push(v);
                if (r <= tm) {
                    addOn(v * 2, tl, tm, l, r, val);
                } else if (l >= tm + 1) {
                    addOn(v * 2 + 1, tm + 1, tr, l, r, val);
                } else {
                    addOn(v * 2, tl, tm, l, tm, val);
                    addOn(v * 2 + 1, tm + 1, tr, tm + 1, r, val);
                }
                t[v].first = t[v * 2].first;
                t[v].gcd = gcd(t[v * 2].gcd, gcd(Math.abs(t[v * 2 + 1].last - t[v * 2].first), t[v * 2 + 1].gcd));
            }
        }
        void addOn(int l, int r, int val) {
            addOn(1, 0, n - 1, l, r, val);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};

        SegmentTree st = new SegmentTree(a);

        System.out.println(st.gcdOn(1, 1));
        st.addOn(2, 2, 3);
        st.addOn(1, 2, 2);
        System.out.println(st.gcdOn(1, 2));
    }
}
