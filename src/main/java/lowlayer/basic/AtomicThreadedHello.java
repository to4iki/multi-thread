package lowlayer.basic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicThreadedHello implements Runnable {

    private AtomicInteger counter;

    public AtomicThreadedHello(int counter) {
        this.counter = new AtomicInteger(counter);
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            counter.getAndIncrement();

            System.out.printf("%s: %d\n", Thread.currentThread().getName(), counter.intValue());
        }
    }
}
