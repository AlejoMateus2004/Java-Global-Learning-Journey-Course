package org.task1.concurrent;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSafeMap<K, V> {
    private final Map<K, V> map = new HashMap<>();

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized Set<K> keySet() {
        return new HashSet<>(map.keySet());
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        ThreadSafeMap<Integer, Integer> map = new ThreadSafeMap<>();

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
            for (int key : map.keySet()) {
                sum += map.get(key);
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
        System.out.println("ThreadSafeMap Duration: " + duration + " ms");
    }
}