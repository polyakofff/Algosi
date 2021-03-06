import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/*
Оформление
*/

public class Main {

    static FastReader in;
    static PrintWriter out;
    static Random rand = new Random();
    static final int INF = (int) (1e9 + 10);
    static final int MOD = (int) (1e9 + 7);
    static final int N = (int) (1e6);
    static final int LOGN = 60;

    static void solve() {
        out.println("LOL");
    }

    public static void main(String[] args) throws FileNotFoundException {
        in = new FastReader(System.in);
//        in = new FastReader(new FileInputStream("input.txt"));
        out = new PrintWriter(System.out);
//        out = new PrintWriter(new FileOutputStream("output.txt"));


        int q = 1;

        while (q-- > 0) {
            solve();
        }

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