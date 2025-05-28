package org.tasks;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerConsumerBlockingQueue {
    private static final int BUFFER_SIZE = 10;
    private final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);

    public void produce(int item) throws InterruptedException {
        buffer.put(item);
        System.out.println("Produced: " + item);
    }

    public int consume() throws InterruptedException {
        int item = buffer.take();
        System.out.println("Consumed: " + item);
        return item;
    }

    public static void main(String[] args) {
        ProducerConsumerBlockingQueue pc = new ProducerConsumerBlockingQueue();

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