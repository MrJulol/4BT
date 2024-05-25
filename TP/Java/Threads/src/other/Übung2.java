package other;

import java.util.Random;
import java.util.stream.IntStream;

public class Übung2 {
    private static int sharedCounter = 0;

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i -> {
            Thread thread = new Thread(()->{
                try {
                    Thread.sleep(new Random().nextInt(100, 5000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " : " + sharedCounter++);
            }, String.valueOf(i));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

