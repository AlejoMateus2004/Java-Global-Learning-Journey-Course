package org.task4;

public class App 
{
    public static void main(String[] args) {
        BlockingObjectPool pool = new BlockingObjectPool(2);

        Thread producer = new Thread(() -> {
            try {
                while (true) {
                    Object obj = new Object();
                    pool.take(obj);
                    System.out.println("Produced: " + obj);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Object obj = pool.get();
                    System.out.println("Consumed: " + obj);
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
