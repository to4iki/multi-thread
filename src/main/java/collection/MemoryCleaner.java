package collection;

import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryCleaner implements Runnable {

    private boolean isAlive = true;
    final private ConcurrentHashMap<String, ConcurrencyCache.UserCache> concurrencyCache;

    public MemoryCleaner(ConcurrentHashMap<String, ConcurrencyCache.UserCache> cache) {
        this.concurrencyCache = cache;
    }

    @Override
    public void run() {
        AtomicInteger count = new AtomicInteger(5);

        while (isAlive) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            Calendar now = Calendar.getInstance();
            Iterator it = concurrencyCache.values().iterator();

            while (it.hasNext()) {
                ConcurrencyCache.UserCache c = (ConcurrencyCache.UserCache) it.next();
                if (c != null) {
                    if (now.after(c.expiredTime)) {
                        it.remove();
                        System.out.println(c.name + "was deleted due to expired time.");
                    }
                }
            }

            if (count.incrementAndGet() > 10)
                isAlive = false;
        }
    }
}
