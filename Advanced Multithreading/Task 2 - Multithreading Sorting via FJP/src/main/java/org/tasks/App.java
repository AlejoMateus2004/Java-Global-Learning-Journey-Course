package org.tasks;

import java.util.Random;

import static org.tasks.ParallelMergeSort.parallelMergeSort;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        int[] smallArray = { 15, 3, 9, 8, 5, 2, 7, 1, 6 };
        int threshold = 2;

        // Generate Large Array Example
        int arraySize = 10_000_000; // 10 million integers
        Random rand = new Random();
        int[] largeArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            largeArray[i] = rand.nextInt(arraySize);
        }

        // Measure time taken for small array
        long startTime = System.currentTimeMillis();
        parallelMergeSort(smallArray, threshold);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.print("Sorted Small Array: ");
        for (int num : smallArray) {
            System.out.print(num + " ");
        }
        System.out.println("\nTime taken for sorting small array: " + duration + " ms");

        // Measure time taken for large array
        startTime = System.currentTimeMillis();
        parallelMergeSort(largeArray, threshold);
        endTime = System.currentTimeMillis();
        duration = endTime - startTime;
        System.out.print("Sorted large Array: ");
        for (int num : largeArray) {
            System.out.print(num + " ");
        }
        System.out.println("\nTime taken for sorting large array: " + duration + " ms");
    }
}
