package Samples;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GraphGenerator {

    static Random rand = new Random();

    static void shuffle(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int j = i + rand.nextInt(n - i);
            Object temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    static class Edge {
        int a, b;

        Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return a == edge.a && b == edge.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(new FileOutputStream("sometext.txt"));

        int n = 50_000;
        out.println(n);

        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        shuffle(a);

        ArrayList<Edge> g = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            int j = rand.nextInt(i);
            g.add(new Edge(a[j], a[i]));
        }

        for (Edge e : g) {
            out.println(e.a + " " + e.b);
        }

        for (int i = 0; i < n; i++) {
            out.print(rand.nextInt(10_000) + " ");
        }
        out.println();

        int m = 50_000;
        out.println(m);
        for (int i = 0; i < m; i++) {
            if (rand.nextInt() % 2 == 1) {
                int v = rand.nextInt(n);
                int u = rand.nextInt(n);
                out.println("F " + v + " " + u);
            } else {
                int v = rand.nextInt(n);
                int u = rand.nextInt(n);
                int val = rand.nextInt(10_000);
                out.println("C " + v + " " + u + " " + val);
            }
        }


        out.close();
    }
}
