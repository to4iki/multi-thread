package lowlayer.reentrant;

import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class EventList {

    private BlockingQueue<String> queue;
    private ReentrantLock lock;

    public EventList() {
        this.queue = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();
    }

    public void set(String name) {
        if (name == null)
            throw new RuntimeException("Exception: name argument is null.");
        if (queue == null)
            throw new NoSuchElementException("Exception: Events is null.");

        try {
            while (!lock.tryLock())
                Thread.sleep(200);
            queue.offer(name); // enqueue

        } catch (InterruptedException ex) {
            ex.printStackTrace();

        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }

        System.out.printf("Set: %d\n", queue.size());
    }

    public synchronized String get() {
        if (queue == null)
            throw new NoSuchElementException("Exception: Events is null.");

        String event = null;
        try {
            while (queue.size() == 0 || !lock.tryLock())
                Thread.sleep(200);
            event = queue.poll(); // dequeue

        } catch (InterruptedException ex) {
            ex.printStackTrace();

        } finally {
            if (lock.isHeldByCurrentThread())
                lock.unlock();
        }

        System.out.printf("Get: %s\n", event);

        return event;
    }
}
