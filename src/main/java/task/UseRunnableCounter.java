package task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UseRunnableCounter {

    public static void main(String[] args) {
        ThreadPoolExecutor.CallerRunsPolicy policy = new ThreadPoolExecutor.CallerRunsPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                System.out.println("Thread-"
                        + Thread.currentThread().getName()
                        + ": Job rejected. ActiveCounts: " + e.getActiveCount());
            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), policy);

        for (int i = 0; i < 20; i++) {
            executor.execute(new RunnableCounter());
        }
        executor.shutdown();
    }
}