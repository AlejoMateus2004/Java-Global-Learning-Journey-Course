package org.tasks;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FibonacciTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10;
    private final int number;

    public FibonacciTask(int number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        if (number <= THRESHOLD) {
            return computeSequentially(number);
        }

        FibonacciTask f1 = new FibonacciTask(number - 1);
        FibonacciTask f2 = new FibonacciTask(number - 2);

        f1.fork();  // Launch f1 asynchronously

        return f2.compute() + f1.join();  // Compute f2 directly and wait for f1
    }

    private long computeSequentially(int n) {
        if (n <= 1) return n;
        long fib = 1;
        long prevFib = 1;

        for (int i = 2; i < n; i++) {
            long temp = fib;
            fib += prevFib;
            prevFib = temp;
        }

        return fib;
    }
}