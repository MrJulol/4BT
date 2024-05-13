import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Parkhaus3 {
    static Semaphore sem = new Semaphore(5);
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> new Thread(()-> {
            try {
                sem.acquire();
                System.out.println("Now Parking: Car " + i);
                Thread.sleep(1000);
                System.out.println("Going away: Car " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                sem.release();
            }
        }).start());
    }
}
