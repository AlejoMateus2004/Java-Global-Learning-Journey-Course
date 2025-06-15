package org.tasks;

import java.util.concurrent.ForkJoinPool;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask task = new FibonacciTask(45);
        long result = pool.invoke(task);
        System.out.println("Fibonacci(45) = " + result);
    }
}
