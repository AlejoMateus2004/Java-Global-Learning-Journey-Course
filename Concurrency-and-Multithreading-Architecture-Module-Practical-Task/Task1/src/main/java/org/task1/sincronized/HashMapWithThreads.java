package org.task1.sincronized;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HashMapWithThreads {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Map<Integer, Integer> map = new HashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable writerTask = () -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, new Random().nextInt(1000));
            }
        };

        // Thread 2: Sum values of the map
        Runnable readerTask = () -> {
            int sum = 0;
            for (int key : map.keySet()) {
                sum += map.get(key);
            }
            System.out.println("Sum: " + sum);
        };

        try {
            // Submit and wait for the writer task to complete
            Future<?> writerFuture = executor.submit(writerTask);
            writerFuture.get(); // wait for the writer task to complete

            // Submit and wait for the reader task to complete
            Future<?> readerFuture = executor.submit(readerTask);
            readerFuture.get(); // wait for the reader task to complete
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        System.out.println("HashMapWithThreads Duration: " + duration + " ms");
    }
}
