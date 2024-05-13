import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Leseschreiber {
    static Semaphore read = new Semaphore(3);
    static Semaphore write = new Semaphore(5);

    public static void main(String[] args) {
        class Pool{
            String name = "pool";
            String lastUser = "";
        }
        Pool pool = new Pool();
        List<String> writers = new ArrayList<>();
        List<String> readers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            writers.add("writer: " + i);
            readers.add("reader: " + i);
        }
        new Thread(()->{
            writers.forEach(writer->{new Thread(()->{
            try {
                write.acquire();
                System.out.println("Wrote to pool");
                pool.lastUser = writer;
                Thread.sleep(new Random().nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                write.release();
            }
        }).start();});}).start();
        new Thread(()->{
            readers.forEach(reader->{new Thread(()->{
            try {
                read.acquire();
                write.acquire(5);
                System.out.println(pool.name + " : " +pool.lastUser);
                Thread.sleep(new Random().nextInt(500, 2000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                read.release();
            }
        }).start();});}).start();




    }
}
