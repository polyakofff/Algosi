package Samples;

import java.util.Scanner;

public class CanonicalFormMatrix {

    static class Fraction {
        int a;
        int b;

        Fraction(int a, int b) {
            this.a = a;
            this.b = b;
        }

        boolean isZero() {
            return a == 0;
        }

        void normalize() {
            int g = gcd(Math.abs(a), Math.abs(b));
            a /= g;
            b /= g;
            if (b < 0) {
                a *= -1;
                b *= -1;
            }
        }

        int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }
    }

    static int m, n;
    static Fraction[][] A;

    static void print() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%d/%d ", A[i][j].a, A[i][j].b);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        m = in.nextInt();
        n = in.nextInt();

        A = new Fraction[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = new Fraction(in.nextInt(), 1);
            }
        }

        int I = 0, J = 0;
        while (I < m && J < n) {
            if (A[I][J].isZero()) {
                int i = I + 1;
                while (i < m && A[i][J].isZero()) i++;
                if (i == m) {
                    J++;
                    continue;
                }
                for (int j = J; j < n; j++) {
                    Fraction temp = A[I][j];
                    A[I][j] = A[i][j];
                    A[i][j] = temp;
                }
            } else {
                for (int i = 0; i < m; i++) {
                    if (i != I) {
                        int xa = A[i][J].a;
                        int xb = A[i][J].b;
                        int ya = A[I][J].a;
                        int yb = A[I][J].b;
                        for (int j = J; j < n; j++) {
                            int a1 = A[I][j].a * -xa * yb;
                            int b1 = A[I][j].b * xb * ya;
                            A[i][j].a = A[i][j].a * b1 + A[i][j].b * a1;
                            A[i][j].b = A[i][j].b * b1;
                            A[i][j].normalize();
                        }
                    }
                }
                int xa = A[I][J].a;
                int xb = A[I][J].b;
                for (int j = J; j < n; j++) {
                    A[I][j].a = A[I][j].a * xb;
                    A[I][j].b = A[I][j].b * xa;
                    A[I][j].normalize();
                }
                I++;
                J++;
            }

            print();
        }
    }
}
