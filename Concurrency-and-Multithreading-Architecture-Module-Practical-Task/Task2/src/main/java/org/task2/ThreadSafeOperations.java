package org.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeOperations {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Lock lock = new ReentrantLock();

        // Thread 1: Writes random integers to the list
        Thread writerThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                lock.lock();
                try {
                    list.add(random.nextInt(100));
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Sums the nums in the list
        Thread sumThread = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    int sum = 0;
                    for (int num : list) {
                        sum += num;
                    }
                    System.out.println("Sum: " + sum);
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 3: Calculates the square root of the sum of squares
        Thread sqrtSumSquaresThread = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    double sumOfSquares = 0;
                    for (int num : list) {
                        sumOfSquares += num * num;
                    }
                    double sqrtSumOfSquares = Math.sqrt(sumOfSquares);
                    System.out.println("Square Root of Sum of Squares: " + sqrtSumOfSquares);
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        writerThread.start();
        sumThread.start();
        sqrtSumSquaresThread.start();
    }
}
