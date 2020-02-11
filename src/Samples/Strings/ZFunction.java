package Samples.Strings;

import java.util.Scanner;

public class ZFunction {

    static Scanner in = new Scanner(System.in);

    static int[] zFunction(char[] s) {
        int n = s.length;
        int[] z = new int[n];
        for (int i = 1, l = 0, r = 0; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s[z[i]] == s[i + z[i]]) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    public static void main(String[] args) {
        char[] s = in.next().toCharArray();
        int[] z = zFunction(s);

        for (int zz : z) {
            System.out.print(zz + " ");
        }
        System.out.println();
    }
}
