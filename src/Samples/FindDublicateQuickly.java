package Samples;

import java.util.Scanner;

public class FindDublicateQuickly {

    static Scanner in = new Scanner(System.in);
    static final int MOD = (int) (1e9 + 7);
    static final int P = 37;
    static int n;
    static int[] a;

    static long binPow(long x, long y) {
        long res = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                res = (res * x) % MOD;
            }
            x = (x * x) % MOD;
            y >>= 1;
        }
        return res;
    }

    static int hash(int x) {
        return (int) binPow(x, P);
    }

    public static void main(String[] args) {
        n = in.nextInt();

        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }

        int l = 1, r = n + 1;
        while (l < r) {
            int m = (l + r) / 2;

            int h = 0;
            for (int i = 1; i <= m; i++) {
                h = (h + hash(i)) % MOD;
            }

            int ah = 0, cnt = 0;
            for (int i = 1; i <= n; i++) {
                if (a[i] <= m) {
                    ah = (ah + hash(a[i])) % MOD;
                    cnt++;
                }
            }

            if (cnt > m || (cnt == m && ah != h)) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        System.out.println(l);
    }
}
