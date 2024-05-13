import java.util.concurrent.Semaphore;

public class ReaderWriterProblem {

    private final Semaphore writeAccess = new Semaphore(1);
    private final Semaphore readAccess = new Semaphore(1);
    private int readerCount = 0;

    public void read() throws InterruptedException {
        readAccess.acquire();
        readerCount++;
        if (readerCount == 1) {
            writeAccess.acquire();
        }
        readAccess.release();

        System.out.println(Thread.currentThread().getName() + " is reading");
        Thread.sleep(1000);

        readAccess.acquire();
        readerCount--;
        if (readerCount == 0) {
            writeAccess.release();
        }
        readAccess.release();
    }

    public void write() throws InterruptedException {
        writeAccess.acquire();

        System.out.println(Thread.currentThread().getName() + " is writing");
        Thread.sleep(1000);

        writeAccess.release();
    }

    public static void main(String[] args) {
        ReaderWriterProblem rw = new ReaderWriterProblem();

        Thread writer1 = new Thread(() -> {
            try {
                rw.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Writer1");

        Thread reader1 = new Thread(() -> {
            try {
                rw.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Reader1");

        Thread reader2 = new Thread(() -> {
            try {
                rw.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Reader2");

        Thread writer2 = new Thread(() -> {
            try {
                rw.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Writer2");

        writer1.start();
        reader1.start();
        reader2.start();
        writer2.start();
    }
}
