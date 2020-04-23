package com.yangfan;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {5, 8, 6, 3, 9, 2, 1, 7};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        //增量gap，并逐步减小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，诸葛对其所在的组进行直接插入排序操作
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                while (j - gap >= 0 && arr[j] < arr[j - gap]) {
                    //插入排序采用交换法
                    swap(arr, j, j - gap);
                    //分组内数据进行交换
                    j -= gap;
                }
            }
        }
    }

    /**
     * 38      * 希尔排序 针对有序序列在插入时采用移动法。
     * 39      * @param arr
     * 40
     */
    public static void sort1(int[] arr) {
        //增量gap，并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素，逐个对其所在组进行直接插入排序操作
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动法
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    arr[j] = temp;
                }
            }
        }
    }

    /**
     * 交换数组元素
     *
     * @param arr 数组对象
     * @param a   j下标
     * @param b   j-gap下标
     */
    static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
