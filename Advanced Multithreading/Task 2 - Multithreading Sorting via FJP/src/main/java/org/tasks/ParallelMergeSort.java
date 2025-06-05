package org.tasks;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {
    public static void parallelMergeSort(int[] array, int threshold) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.invoke(new SortTask(array, new int[array.length], 0, array.length - 1, threshold));
        } finally {
            pool.shutdown();
        }
    }

    private static class SortTask extends RecursiveAction {
        private final int[] array;
        private final int[] temp;
        private final int left;
        private final int right;
        private final int threshold;

        SortTask(int[] array, int[] temp, int left, int right, int threshold) {
            this.array = array;
            this.temp = temp;
            this.left = left;
            this.right = right;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            if (right - left < threshold) {
                sequentialSort(array, left, right);
            } else {
                int middle = (left + right) / 2;
                invokeAll(new SortTask(array, temp, left, middle, threshold),
                        new SortTask(array, temp, middle + 1, right, threshold));
                merge(array, temp, left, middle, right);
            }
        }

        private void sequentialSort(int[] array, int left, int right) {
            for (int i = left; i <= right; i++) {
                for (int j = i; j > left && array[j - 1] > array[j]; j--) {
                    swap(array, j, j - 1);
                }
            }
        }

        private void merge(int[] array, int[] temp, int left, int middle, int right) {
            System.arraycopy(array, left, temp, left, right - left + 1);

            int i = left;
            int j = middle + 1;
            int k = left;

            while (i <= middle && j <= right) {
                if (temp[i] <= temp[j]) {
                    array[k++] = temp[i++];
                } else {
                    array[k++] = temp[j++];
                }
            }

            while (i <= middle) {
                array[k++] = temp[i++];
            }

            while (j <= right) {
                array[k++] = temp[j++];
            }
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

}
