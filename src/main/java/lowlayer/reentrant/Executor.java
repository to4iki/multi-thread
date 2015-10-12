package lowlayer.reentrant;

/**
 * Reentrant Lock
 */
public class Executor {

    public static void main(String[] args) {

        final Thread[] gthread = new Thread[3];
        final Thread[] sthread = new Thread[3];

        final EventList eventList = new EventList();
        final EventGet eventGet = new EventGet(eventList);
        final EventSet eventSet = new EventSet(eventList);

        for (int i = 0; i < 3; i++) {
            gthread[i] = new Thread(eventGet);
            sthread[i] = new Thread(eventSet);
        }

        for (int i = 0; i < 3; i++) {
            gthread[i].start();
            sthread[i].start();
        }

        try {
            for (int i = 0; i < 3; i++) {
                gthread[i].join();
                sthread[i].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
