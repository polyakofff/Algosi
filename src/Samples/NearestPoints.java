package Samples;

import java.util.*;

public class NearestPoints {

    static Scanner in;

    static class Point {
        int x, y, i;

        Point(int x, int y, int i) {
            this.x = x;
            this.y = y;
            this.i = i;
        }
    }

    static Point[] points;
    static double minDist = Double.MAX_VALUE;
    static int ans1 = -1, ans2 = -1;

    static class XCopmarator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.x == o2.x ? o1.y - o2.y : o1.x - o2.x;
        }
    }
    static class YCopmarator implements Comparator<Point> {
        @Override
        public int compare(Point o1, Point o2) {
            return o1.y - o2.y;
        }
    }
    static XCopmarator byx = new XCopmarator();
    static YCopmarator byy = new YCopmarator();

    static void updAns(Point p1, Point p2) {
        double d = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
        if (d < minDist) {
            minDist = d;
            ans1 = p1.i;
            ans2 = p2.i;
        }
    }

    static void merge(Point[] temp, int l, int m, int r) {
        for (int i = l, j = m; i < m || j < r;) {
            if (j == r || (i < m && points[i].y <= points[j].y)) {
                temp[i - l + j - m] = points[i];
                i++;
            } else {
                temp[i - l + j - m] = points[j];
                j++;
            }
        }
        System.arraycopy(temp, 0, points, l, r - l);
    }

    static void calc(int l, int r) {
        if (r - l < 4) {
            for (int i = l; i < r - 1; i++) {
                for (int j = i + 1; j < r; j++) {
                    updAns(points[i], points[j]);
                }
            }
            Arrays.sort(points, l, r, byy);
            return;
        }

        int m = (l + r + 1) >> 1;
        int midx = points[m].x;

        calc(l, m);
        calc(m, r);
        Point[] temp = new Point[r - l];
        merge(temp, l, m, r);

        int cur = 0;
        for (int i = l; i < r; i++) {
            if (Math.abs(points[i].x - midx) >= minDist) continue;

            for (int j = cur - 1; j >= 0 && points[i].y - temp[j].y < minDist; j--) {
                updAns(points[i], temp[j]);
            }
            temp[cur++] = points[i];
        }
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);

        int n = in.nextInt();

        points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            points[i] = new Point(x, y, i);
        }

        Arrays.sort(points, byx);

        calc(0, n);

        System.out.println(minDist);
        System.out.println((ans1 + 1) + " " + (ans2 + 1));
    }
}
