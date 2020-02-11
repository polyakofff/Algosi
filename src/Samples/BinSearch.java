package Samples;

public class BinSearch {

    static int lowerBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >> 1;
            if (a[m] < x)
                l = m + 1;
            else
                r = m;
        }
        return l;
    }

    static int upperBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >> 1;
            if (a[m] <= x)
                l = m + 1;
            else
                r = m;
        }
        return l;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 3, 4, 6, 9, 9, 10};

        System.out.println(lowerBound(a, 1));
        System.out.println(lowerBound(a, 5));
    }
}
