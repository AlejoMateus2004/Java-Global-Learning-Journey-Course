package org.task3;

public class Consumer implements Runnable {
    private final MessageBus messageBus;
    private final String name;

    public Consumer(MessageBus messageBus, String name) {
        this.messageBus = messageBus;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = messageBus.consumeMessage();
                System.out.println(name + " consumed: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}