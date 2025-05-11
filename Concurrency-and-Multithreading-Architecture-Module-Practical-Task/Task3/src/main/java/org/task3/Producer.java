package org.task3;

import java.util.Random;

public class Producer implements Runnable {
    private final MessageBus messageBus;
    private final String name;
    private final Random random = new Random();

    public Producer(MessageBus messageBus, String name) {
        this.messageBus = messageBus;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            String message = "Message from " + name + ": " + random.nextInt(100);
            messageBus.postMessage(message);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}