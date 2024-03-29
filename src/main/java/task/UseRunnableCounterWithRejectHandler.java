package task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseRunnableCounterWithRejectHandler {

    public static void main(String[] args) {
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("Thread-"
                        + Thread.currentThread().getName()
                        + ": Job rejected. ActiveCounts: " + executor.getActiveCount());
                executor.getQueue().add(r); // re-enqueue
            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), handler);

        for (int i = 0; i < 20; i++) {
            executor.execute(new RunnableCounter());
        }
        executor.shutdown();
    }
}