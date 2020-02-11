package Samples;

public class ExtendedEuclideanAlgo {

    static int x0, y0;
    static int gcd(int a, int b) {
        if (b == 0) {
            x0 = 1;
            y0 = 0;
            return a;
        }
        int g = gcd(b, a % b);
        int x1 = x0;
        int y1 = y0;
        x0 = y1;
        y0 = x1 - (a / b) * y1;
        return g;
    }

    public static void main(String[] args) {
        int a = 5;
        int b = 3;
        int g = gcd(a, b);
        System.out.println(g + " " + x0 + " " + y0);
    }
}
