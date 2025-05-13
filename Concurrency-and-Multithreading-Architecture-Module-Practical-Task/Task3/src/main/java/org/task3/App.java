package org.task3;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        MessageBus messageBus = new MessageBus();

        // Create and Start producers
        for (int i = 1; i <= 3; i++) {
            Producer producer = new Producer(messageBus, "Producer-" + i);
            new Thread(producer).start();
        }
        // Create and Start consumers
        for (int i = 1; i <= 3; i++) {
            Consumer consumer = new Consumer(messageBus, "Consumer-" + i);
            new Thread(consumer).start();
        }
    }
}
