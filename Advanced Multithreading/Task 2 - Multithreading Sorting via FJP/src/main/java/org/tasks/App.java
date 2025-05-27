package org.tasks;

import static org.tasks.ParallelMergeSort.parallelMergeSort;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        int[] array = { 15, 3, 9, 8, 5, 2, 7, 1, 6 };
        int threshold = 2;

        parallelMergeSort(array, threshold);

        System.out.print("Sorted Array: ");
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
