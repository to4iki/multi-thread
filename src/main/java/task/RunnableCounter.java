package task;

import java.util.concurrent.atomic.AtomicLong;

public class RunnableCounter implements Runnable {

    final private AtomicLong num = new AtomicLong(0);

    private void increment() {
        num.getAndIncrement();
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            increment();
        }
        System.out.println("Thread-" + Thread.currentThread().getName());
    }
}
