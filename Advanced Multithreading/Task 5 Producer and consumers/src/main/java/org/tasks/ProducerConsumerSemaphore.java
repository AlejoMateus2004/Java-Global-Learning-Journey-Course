package org.tasks;

import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

class ProducerConsumerSemaphore {
    private static final int BUFFER_SIZE = 10;
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore items = new Semaphore(0);
    private final Semaphore space = new Semaphore(BUFFER_SIZE);

    public void produce(int item) throws InterruptedException {
        space.acquire();
        mutex.acquire();
        buffer.add(item);
        System.out.println("Produced: " + item);
        mutex.release();
        items.release();
    }

    public int consume() throws InterruptedException {
        items.acquire();
        mutex.acquire();
        int item = buffer.poll();
        System.out.println("Consumed: " + item);
        mutex.release();
        space.release();
        return item;
    }

    public static void main(String[] args) {
        ProducerConsumerSemaphore pc = new ProducerConsumerSemaphore();

        // Create producer thread
        Thread producerThread = new Thread(() -> {
            try {
                int i = 0;
                while (true) {
                    pc.produce(i++);
                    Thread.sleep(100); // simulate time delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Create consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    pc.consume();
                    Thread.sleep(150); // simulate time delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start both threads
        producerThread.start();
        consumerThread.start();
    }
}