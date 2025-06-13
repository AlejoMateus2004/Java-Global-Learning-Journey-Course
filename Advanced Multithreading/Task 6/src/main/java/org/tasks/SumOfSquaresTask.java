package org.tasks;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;

public class SumOfSquaresTask extends RecursiveAction {
    private static final int THRESHOLD = 1_000;
    private final double[] array;
    private final int start;
    private final int end;
    private double sum;

    public SumOfSquaresTask(double[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            sum = computeDirectly();
        } else {
            int mid = (start + end) / 2;
            SumOfSquaresTask leftTask = new SumOfSquaresTask(array, start, mid);
            SumOfSquaresTask rightTask = new SumOfSquaresTask(array, mid, end);
            invokeAll(leftTask, rightTask);
            sum = leftTask.getSum() + rightTask.getSum();
        }
    }

    private double computeDirectly() {
        double localSum = 0;
        for (int i = start; i < end; i++) {
            localSum += array[i] * array[i];
        }
        return localSum;
    }

    public double getSum() {
        return sum;
    }

    public static void main(String[] args) {
        double[] array = new double[500_000_000];
        Arrays.setAll(array, i -> ThreadLocalRandom.current().nextDouble());

        // Measure time of RecursiveAction
        ForkJoinPool pool = new ForkJoinPool();
        SumOfSquaresTask task = new SumOfSquaresTask(array, 0, array.length);

        long startTime = System.currentTimeMillis();
        pool.invoke(task);
        double recursiveSum = task.getSum();
        long endTime = System.currentTimeMillis();
        System.out.println("RecursiveAction Sum: " + recursiveSum);
        System.out.println("Time taken by RecursiveAction: " + (endTime - startTime) + " ms");

        // Measure time of direct calculation using for loop
        double sum = 0;
        startTime = System.currentTimeMillis();
        for (double v : array) {
            sum += v * v;
        }
        endTime = System.currentTimeMillis();
        System.out.println("Direct for loop Sum: " + sum);
        System.out.println("Time taken by direct for loop: " + (endTime - startTime) + " ms");

        // Measure time of direct calculation using Stream API
        startTime = System.currentTimeMillis();
        double streamSum = Arrays.stream(array).map(v -> v * v).sum();
        endTime = System.currentTimeMillis();
        System.out.println("Stream API Sum: " + streamSum);
        System.out.println("Time taken by Stream API: " + (endTime - startTime) + " ms");
    }
}