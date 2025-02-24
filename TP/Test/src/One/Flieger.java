package One;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Flieger implements Runnable {
    private int name;
    private Tower tower;
    private CountDownLatch countDownLatch;
    private Feuerwehr feuerwehr;
    private int free;

    public Flieger(int name, Tower tower, CountDownLatch latch, Feuerwehr feuerwehr) {
        this.name = name;
        this.tower = tower;
        this.countDownLatch = latch;
        this.feuerwehr = feuerwehr;
    }

    @Override
    public void run() {
        boolean willCrash = false;
        if (new Random().nextInt(0, 10) < 4) {
            willCrash = true;
        }
        System.out.println("One.Flieger " + name + " ist gestartet");
        try {
            Thread.sleep(new Random().nextInt(1000, 2000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("One.Flieger" + name + " beginnt Landeanflug");
        try {
            System.out.println("One.Flieger " + name + " wartet auf Bahn");
            this.free = tower.getBahnen().availablePermits();
            System.out.println(free);
            tower.getBahnen().acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            if (willCrash) {
                System.out.println("One.Flieger " + name + " ist abgestürzt auf Bahn");
                this.feuerwehr.notifyFeuerwehr();
                try {
                    this.feuerwehr.getLatch().await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Bahn wieder frei");
                tower.getBahnen().release();
                countDownLatch.countDown();
            } else {
                System.out.println("One.Flieger "+ name + " ist gelandet auf Bahn");
                tower.getBahnen().release();
                countDownLatch.countDown();
            }
        }

    }
}
