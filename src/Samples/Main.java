package Samples;

import java.io.*;
import java.util.*;

public class Main {

    static FastReader in;
    static PrintWriter out;

    static void solve() {
    }

    public static void main(String[] args) {
        in = new FastReader(System.in);
//        in = new FastReader(new FileInputStream("input.txt"));
        out = new PrintWriter(System.out);
//        out = new PrintWriter(new FileOutputStream("output.txt"));


        int q = 1;

        while (q-- > 0)
            solve();

        out.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        FastReader(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        Integer nextInt() {
            return Integer.parseInt(next());
        }

        Long nextLong() {
            return Long.parseLong(next());
        }

        Double nextDouble() {
            return Double.parseDouble(next());
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(nextLine());
            }
            return st.nextToken();
        }

        String nextLine() {
            String s = "";
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }
    }
}
