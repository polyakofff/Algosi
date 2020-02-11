package Samples.DataStructures;

import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

public class Treap_Struct {

    static class Treap {
        static final int INF = (int) (1e9);
        static Random rand = new Random();
        Node root, m, l, r;

        class Node {
            int x, y;
            int val, max;
            int sum;
            int add, color;
            int cnt;
            Node l, r;

            Node(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
                max = val;
                sum = val;
                add = 0;
                color = -1;
                cnt = 1;
                l = r = null;
            }
        }

        int cnt(Node t) {
            if (t != null) {
                return t.cnt;
            }
            return 0;
        }
        int max(Node t) {
            if (t != null) {
                if (t.color != -1) {
                    return t.color + t.add;
                } else {
                    return t.max + t.add;
                }
            }
            return -INF;
        }
        int sum(Node t) {
            if (t != null) {
                if (t.color != -1) {
                    return (t.color + t.add) * t.cnt;
                } else {
                    return t.sum + t.add * t.cnt;
                }
            }
            return 0;
        }

        void push(Node t) {
            if (t.color != -1) {
                if (t.l != null) {
                    t.l.add = 0;
                    t.l.color = t.color;
                }
                if (t.r != null) {
                    t.r.add = 0;
                    t.r.color = t.color;
                }
                t.val = t.color;
                t.color = -1;
            }
            if (t.add != 0) {
                if (t.l != null) {
                    t.l.add += t.add;
                }
                if (t.r != null) {
                    t.r.add += t.add;
                }
                t.val += t.add;
                t.add = 0;
            }
        }

        void recalc(Node t) {
            t.cnt = 1 + cnt(t.l) + cnt(t.r);
            t.max = Math.max(t.val, Math.max(max(t.l), max(t.r)));
            t.sum = t.val + sum(t.l) + sum(t.r);
        }

        void merge(Node t1, Node t2) {
            if (t1 == null || t2 == null) {
                m = t1 == null ? t2 : t1;
                return;
            }
            if (t1.y >= t2.y) {
                push(t1);
                merge(t1.r, t2);
                t1.r = m;
                m = t1;
            } else {
                push(t2);
                merge(t1, t2.l);
                t2.l = m;
                m = t2;
            }
            recalc(m);
        }

        // [x1, x2] -> [x1, x] (x, x2]
        void split(Node t, int x) {
            if (t == null) {
                l = r = null;
                return;
            }
            push(t);
            if (t.x <= x) {
                split(t.r, x);
                t.r = l;
                l = t;
                recalc(l);
            } else {
                split(t.l, x);
                t.l = r;
                r = t;
                recalc(r);
            }
        }

        void update(Node t) {
            if (t != null) {
                push(t);
                update(t.l);
                update(t.r);
                recalc(t);
            }
        }


        void build(int[] xs, int[] vals) {
            Stack<Node> st = new Stack<>();
            for (int i = 0; i < vals.length; i++) {
                Node curr = new Node(xs[i], rand.nextInt(), vals[i]);
                Node last = null;
                while (!st.empty() && st.peek().y < curr.y) {
                    last = st.pop();
                }
                curr.l = last;
                if (st.empty()) {
                    root = curr;
                } else {
                    st.peek().r = curr;
                }
                st.push(curr);
            }

            update(root);
        }

        void put(int x, int val) {
            split(root, x);
            Node t = new Node(x, rand.nextInt(), val);
            merge(l, t);
            merge(m, r);
            root = m;
        }

        void remove(int x) {
            split(root, x - 1);
            Node t = l;
            split(r, x);
            merge(t, r);
            root = m;
        }

        int kth(Node t, int k) {
            int i = cnt(t.l);
            if (k < i) {
                return kth(t.l, k);
            } else if (k > i) {
                return kth(t.r, k - i - 1);
            } else {
                return t.val;
            }
        }
        int kth(int k) {
            if (k < 0 || k >= cnt(root)) {
                throw new IndexOutOfBoundsException();
            }
            return kth(root, k);
        }

        // [x1, x2)
        int maxOn(int x1, int x2) {
            split(root, x1 - 1);
            Node t = l;
            split(r, x2 - 1);
            int res = max(l);
            merge(t, l);
            merge(m, r);
            root = m;
            return res;
        }
        // [x1, x2)
        int sumOn(int x1, int x2) {
            split(root, x1 - 1);
            Node t = l;
            split(r, x2 - 1);
            int res = sum(l);
            merge(t, l);
            merge(m, r);
            root = m;
            return res;
        }

        // [x1, x2)
        void addOn(int x1, int x2, int val) {
            split(root, x1 - 1);
            Node t = l;
            split(r, x2 - 1);
            if (l != null) {
                l.add += val;
            }
            merge(t, l);
            merge(m, r);
            root = m;
        }
        // [x1, x2)
        void colorOn(int x1, int x2, int val) {
            split(root, x1 - 1);
            Node t = l;
            split(r, x2 - 1);
            if (l != null) {
                l.add = 0;
                l.color = val;
            }
            merge(t, l);
            merge(m, r);
            root = m;
        }
    }

    public static void main(String[] args) {
        Treap tr = new Treap();

        tr.put(1, 1);
        tr.put(2, 2);
        tr.put(3, 3);
        tr.put(4, 4);
        tr.addOn(1, 3, 1);
        tr.colorOn(2, 4, 10);

        System.out.println(tr.sumOn(1, 10));
    }
}
