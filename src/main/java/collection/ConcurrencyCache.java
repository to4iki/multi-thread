package collection;

import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread safe cache
 */
public class ConcurrencyCache {

    protected class UserCache {
        public String name;
        public Calendar expiredTime;

        public UserCache() {
        }

        public UserCache(String name, Calendar expiredTime) {
            this.name = name;
            this.expiredTime = expiredTime;
        }
    }

    final private ConcurrentHashMap<String, UserCache> concurrencyCache = new ConcurrentHashMap<>();

    public UserCache add(String name) {
        UserCache cache = new UserCache();
        if (name != null) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.SECOND, 1);

            cache = new UserCache(name, now);

            if (concurrencyCache.putIfAbsent(name, cache) == null) {
                System.out.println(name + " was not mapped. Put the new name into the cache.");
            } else {
                System.out.println(name + " already exist in the cache. Terminates the thread.");
            }
        }

        return cache;
    }

    public ConcurrentHashMap<String, UserCache> getConcurrentCache() {
        return concurrencyCache;
    }

    public Optional<UserCache> getUserCache(String name) {
        return Optional.ofNullable(concurrencyCache.get(name));
    }
}
