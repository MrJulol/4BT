import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Feuerwehr {
    private CountDownLatch latch = new CountDownLatch(1);
    public void notifyFeuerwehr(){
        System.out.println("Feuerwehr löscht");
        try {
            Thread.sleep(new Random().nextInt(1000, 3000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
