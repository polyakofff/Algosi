package Samples.DataStructures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class MinDeq_Struct {

    static class MinDeq<T extends Comparable<T>> {
        Stack<Pair<T>> l;
        Stack<Pair<T>> r;

        MinDeq() {
            l = new Stack<>();
            r = new Stack<>();
        }

        boolean empty() {
            return l.empty() && r.empty();
        }

        void pushFirst(T x) {
            l.push(new Pair<>(x, l.empty() ? x : min(l.peek().min, x)));
        }

        void pushLast(T x) {
            r.push(new Pair<>(x, r.empty() ? x : min(r.peek().min, x)));
        }

        void popFirst() {
            if (empty()) {
                return;
            }
            if (l.empty()) {
                int size = r.size();
                Stack<Pair<T>> local = new Stack<>();
                for (int i = 0; i < size / 2; i++) {
                    local.push(r.pop());
                }
                while (!r.empty()) {
                    Pair<T> p = r.pop();
                    l.push(new Pair<>(p.x, l.empty() ? p.x : min(l.peek().min, p.x)));
                }
                while (!local.empty()) {
                    Pair<T> p = local.pop();
                    r.push(new Pair<>(p.x, r.empty() ? p.x : min(r.peek().min, p.x)));
                }
            }
            l.pop();
        }

        void popLast() {
            if (empty()) {
                return;
            }
            if (r.empty()) {
                int size = l.size();
                Stack<Pair<T>> local = new Stack<>();
                for (int i = 0; i < size / 2; i++) {
                    local.push(l.pop());
                }
                while (!l.empty()) {
                    Pair<T> p = l.pop();
                    r.push(new Pair<>(p.x, r.empty() ? p.x : min(r.peek().min, p.x)));
                }
                while (!local.empty()) {
                    Pair<T> p = local.pop();
                    l.push(new Pair<>(p.x, l.empty() ? p.x : min(l.peek().min, p.x)));
                }
            }
            r.pop();
        }

        T getMin() {
            if (empty()) {
                return null;
            } else if (l.empty()) {
                return r.peek().min;
            } else if (r.empty()) {
                return l.peek().min;
            } else {
                return min(l.peek().min, r.peek().min);
            }
        }

        T min(T x1, T x2) {
            return x1.compareTo(x2) <= 0 ? x1 : x2;
        }

        class Pair<T> {
            T x, min;

            Pair(T x, T min) {
                this.x = x;
                this.min = min;
            }
        }
    }

    public static void main(String[] args) {
        MinDeq<Integer> md = new MinDeq<>();

        md.pushLast(1);
        md.pushLast(2);
        md.pushLast(3);
        md.pushLast(4);
        System.out.println(md.getMin());
        md.popFirst();
        md.popFirst();
        md.popFirst();
        System.out.println(md.getMin());
    }
}
