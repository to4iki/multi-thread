package task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class UseCallableCounter {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Integer>> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            CallableCounter counter = new CallableCounter();
            Future<Integer> result = executor.submit(counter);
            list.add(result);
        }

        for (Future<Integer> x : list) {
            Integer num = 0;

            try {
                num = x.get();
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }

            System.out.println("Thread-" + Thread.currentThread().getName() + ": counter=" + num);
        }

        executor.shutdown();
    }
}