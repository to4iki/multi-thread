package lowlayer.synchronize;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class EventList {

    private final int MAX_EVENT_SIZE = 5;
    private LinkedList<String> events;

    public EventList() {
        this.events = new LinkedList<>();
    }

    public synchronized void set(String name) {
        if (name == null)
            throw new RuntimeException("Exception: name argument is null.");
        if (events == null)
            throw new NoSuchElementException("Exception: Events is null.");

        while (events.size() == MAX_EVENT_SIZE) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        events.offer(name); // enqueue
        System.out.printf("Set: %d\n", events.size());
        notifyAll();
    }

    public synchronized String get() {
        if (events == null)
            throw new NoSuchElementException("Exception: Events is null.");

        while (events.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        String event = events.poll(); // dequeue
        System.out.printf("Get: %s\n", event);
        notifyAll();

        return event;
    }
}
