package org.task1.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashMapWithThreads {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Map<Integer, Integer> map = new HashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Thread 1: Add elements to the map
        executor.submit(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, new Random().nextInt(1000));
            }
        });

        // Thread 2: Sum values of the map
        executor.submit(() -> {
            int sum = 0;
            while (true) {
                try {
                    for (int key : map.keySet()) {
                        sum += map.get(key);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Caught ConcurrentModificationException");
                }
            }
            System.out.println("Sum: " + sum);
        });

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        System.out.println("HashMapWithThreads Duration: " + duration + " ms");
    }
}
