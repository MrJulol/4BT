package other;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Latch {
    public static int counter = 1;
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        ArrayList<Thread> threads = new ArrayList<>();
        IntStream.range(0, 5).forEach(i -> threads.add(new Thread(()->{
            countUp();
            latch.countDown();
        })));
        new Thread(()->{
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("LOS!");
            }
        }).start();
        threads.forEach(Thread::start);
    }
    public static synchronized void countUp() {
        System.out.println(counter);
        counter++;
    }
}
