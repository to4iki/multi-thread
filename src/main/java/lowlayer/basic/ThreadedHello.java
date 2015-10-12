package lowlayer.basic;

public class ThreadedHello implements Runnable {

    public int counter;

    public ThreadedHello(int counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            synchronized (this) {
                counter++;
            }

            System.out.printf("%s: %d\n", Thread.currentThread().getName(), counter);
        }
    }
}
