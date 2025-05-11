package org.task3;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {
    private final List<String> messages = new ArrayList<>();
    private final Object lock = new Object();

    public void postMessage(String message) {
        synchronized (lock) {
            messages.add(message);
            lock.notifyAll();
        }
    }

    public String consumeMessage() throws InterruptedException {
        synchronized (lock) {
            while (messages.isEmpty()) {
                lock.wait();
            }
            return messages.remove(0);
        }
    }
}