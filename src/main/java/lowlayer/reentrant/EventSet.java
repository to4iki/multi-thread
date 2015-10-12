package lowlayer.reentrant;

public class EventSet implements Runnable {

    private final EventList eventList;

    public EventSet(EventList eventList) {
        this.eventList = eventList;
    }

    @Override
    public void run() {
        for (Integer i = 0; i < 10; i++) {
            eventList.set(i.toString());
        }
    }
}
