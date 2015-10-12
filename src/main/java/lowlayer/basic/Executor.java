package lowlayer.basic;

public class Executor {

    public static void main(String[] args) {
//        ThreadedHello hello = new ThreadedHello(0);
        AtomicThreadedHello hello = new AtomicThreadedHello(0);
        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(hello);
        }

        for (int i = 0; i < 3; i++) {
            threads[i].start();
        }

        try {
            for (int i = 0; i < 3; i++) {
                threads[i].join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
