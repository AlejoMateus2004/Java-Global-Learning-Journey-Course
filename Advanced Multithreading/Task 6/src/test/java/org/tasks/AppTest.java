package org.tasks;

import org.junit.Test;


import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testFibonacciTask() {
        ForkJoinPool pool = new ForkJoinPool();
        FibonacciTask task = new FibonacciTask(45);
        long result = pool.invoke(task);
        assertEquals(1134903170L, result);
    }
}
