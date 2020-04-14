package com.yangfan;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static void main(String[] args) {
        int[] a = new int[]{3, 5, 7, 2, 1, 9, 8, 6, 4};

        System.out.println("Random Array :");
        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println("Quick Sort :");
        quickSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    static void quickSort(int[] a, int low, int hight) {
        int pivot;
        if (low < hight) {
            pivot = partition(a, low, hight);
            quickSort(a, low, pivot - 1);
            quickSort(a, pivot + 1, hight);
        }
    }

    static int partition(int[] a, int low, int high) {
        int pivotkey = a[low];
        while (low < high) {
            //右边的大于基准数
            while (high > low && a[high] >= pivotkey) {
                high--;
            }
            a[low] = a[high];
            while (high > low && a[low] <= pivotkey) {
                low++;
            }
            a[high] = a[low];
        }

        a[low] = pivotkey;
        return low;
    }
}
