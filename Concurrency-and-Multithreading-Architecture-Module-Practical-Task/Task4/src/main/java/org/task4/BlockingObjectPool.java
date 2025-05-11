package org.task4;

import java.util.ArrayList;
import java.util.List;

public class BlockingObjectPool {
    private final List<Object> pool;
    private final int size;

    public BlockingObjectPool(int size) {
        this.size = size;
        this.pool = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Object());
        }
    }

    public synchronized Object get() {
        while (pool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Object obj = pool.remove(pool.size() - 1);
        notifyAll();
        return obj;
    }

    public synchronized void take(Object object) {
        while (pool.size() == size) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        pool.add(object);
        notifyAll();
    }
}