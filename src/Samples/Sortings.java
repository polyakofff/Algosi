package Samples;

import java.io.*;
import java.util.*;

public class Sortings {

    private static Random rand = new Random();

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    // array shufle
    private static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            if (j != i) {
                a[i] ^= a[j];
                a[j] ^= a[i];
                a[i] ^= a[j];
            }
        }
    }
    //



//    // quick sort
//    private static int partition(int[] a, int l, int r) {
//        swap(a, r, l + rand.nextInt(r - l + 1));
//        int pivot = a[r];
//
//        int pi = l;
//        for (int i = l; i < r; i++) {
//            if (a[i] < pivot) {
//                swap(a, i, pi);
//                pi++;
//            }
//        }
//        swap(a, r, pi);
//
//        return pi;
//    }
//
//    private static void quickSort(int[] a, int l, int r) {
//        if (l >= r) return;
//
//        int pi = partition(a, l, r);
//
//        quickSort(a, l, pi - 1);
//        quickSort(a, pi + 1, r);
//    }
//    //

    // Quick sort
    static void quickSort(int[] a, int l, int r) {
        if (l >= r) return;
        int i = l, j = r;
        int m = l + rand.nextInt(r - l + 1);
        while (i < j) {
            while (i < m && a[i] < a[m]) i++;
            while (j > m && a[j] >= a[m]) j--;
            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                if (m == i) {
                    m = j;
                } else if (m == j) {
                    m = i;
                }
            }
        }
        quickSort(a, l, m - 1);
        quickSort(a, m + 1, r);
    }





    // Merge sort
    private static void merge(int[] a, int l, int m, int r) {
        int[] temp = new int[r - l];
        for (int i = l, j = m; i < m || j < r;) {
            if (j == r || (i != m && a[i] < a[j]))
                temp[i - l + j - m] = a[i++];
            else
                temp[i - l + j - m] = a[j++];
        }
        System.arraycopy(temp, 0, a, l, r - l);
    }

    private static void mergeSort(int[] a, int l, int r) {
        if (l >= r) return;

        int m = (l + r) / 2;

        mergeSort(a, l, m);
        mergeSort(a, m + 1, r);
        merge(a, l, m + 1, r + 1);
    }
    //




    public static void main(String[] args) {
        int n = 1_000_000;
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt();
        }

        long startTime = System.currentTimeMillis();

        quickSort(a, 0, a.length - 1);

//        mergeSort(a, 0, n - 1);

        System.out.println((System.currentTimeMillis() - startTime) + "ms");
    }
}
