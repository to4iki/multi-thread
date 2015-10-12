package collection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UseConcurrencyCache {

    public static void main(String[] args) {
        final ExecutorService executor = Executors.newCachedThreadPool();
        final ConcurrencyCache cache = new ConcurrencyCache();
        final MemoryCleaner cleaner = new MemoryCleaner(cache.getConcurrentCache());

        final Thread addThread = new Thread(() -> {
            final String name = "test";
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                cache.add(name);
            }
        });

        final Thread cleanerThread = new Thread(cleaner);

        executor.execute(addThread);
        executor.execute(cleanerThread);

        executor.shutdown();
    }
}
