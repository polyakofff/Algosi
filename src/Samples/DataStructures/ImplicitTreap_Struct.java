package Samples.DataStructures;

import java.util.Random;
import java.util.Stack;

public class ImplicitTreap_Struct {

    static class ImplicitTreap {
        static final int INF = (int) (1e9);
        static Random rand = new Random();
        Node root, m, l, r;

        class Node {
            int y;
            int val, max;
            int sum;
            int add, color;
            int rev;
            int cnt;
            Node l, r;

            Node(int y, int val) {
                this.y = y;
                this.val = val;
                max = val;
                sum = val;
                add = 0;
                color = -1;
                rev = 0;
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
            if (t.rev == 1) {
                if (t.l != null) {
                    t.l.rev ^= 1;
                }
                if (t.r != null) {
                    t.r.rev ^= 1;
                }
                Node t1 = t.l;
                t.l = t.r;
                t.r = t1;
                t.rev = 0;
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
            int i = cnt(t.l) + 1;
            if (i <= x) {
                split(t.r, x - i);
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


        void build(int[] vals) {
            Stack<Node> st = new Stack<>();
            for (int i = 0; i < vals.length; i++) {
                Node curr = new Node(rand.nextInt(), vals[i]);
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

        void put(int i, int val) {
            split(root, i);
            Node t = new Node(rand.nextInt(), val);
            merge(l, t);
            merge(m, r);
            root = m;
        }

        void remove(int x) {
            split(root, x - 1);
            Node t = l;
            split(r, 1);
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

        // [i, j)
        int maxOn(int i, int j) {
            split(root, i);
            Node t = l;
            split(r, j - i);
            int res = max(l);
            merge(t, l);
            merge(m, r);
            root = m;
            return res;
        }
        // [i, j)
        int sumOn(int i, int j) {
            split(root, i);
            Node t = l;
            split(r, j - i);
            int res = sum(l);
            merge(t, l);
            merge(m, r);
            root = m;
            return res;
        }

        // [i, j)
        void addOn(int i, int j, int val) {
            if (i >= j) return;
            split(root, i);
            Node t = l;
            split(r, j - i);
            l.add += val;
            merge(t, l);
            merge(m, r);
            root = m;
        }
        // [i, j)
        void colorOn(int i, int j, int val) {
            if (i >= j) return;
            split(root, i);
            Node t = l;
            split(r, j - i);
            l.add = 0;
            l.color = val;
            merge(t, l);
            merge(m, r);
            root = m;
        }
        // [i, j)
        void revOn(int i, int j) {
            if (i >= j) return;
            split(root, i);
            Node t = l;
            split(r, j - i);
            l.rev ^= 1;
            merge(t, l);
            merge(m, r);
            root = m;
        }
    }

    public static void main(String[] args) {

    }
}
