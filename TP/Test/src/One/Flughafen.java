package One;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Flughafen {

    private static final int NR_OF_FLIEGER = 5;
    private static final int NR_OF_LANDEBAHNEN = 3;

    public static void main(String[] args) {
        Tower tower = new Tower(NR_OF_LANDEBAHNEN);
        Feuerwehr feuerwehr = new Feuerwehr();
        CountDownLatch latch = new CountDownLatch(NR_OF_FLIEGER);
        ExecutorService executorService = Executors.newFixedThreadPool(NR_OF_FLIEGER);

        for (int i = 0; i < NR_OF_FLIEGER; i++) {
            executorService.submit(new Flieger(i, tower, latch, feuerwehr));
        }

        executorService.shutdown();
        try {
            latch.await();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Alle Flugzeuge sind am Boden");
            System.out.println("Flughafensimulation abgeschlossen");
        }
    }
}