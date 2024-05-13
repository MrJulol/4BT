import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class Parkhaus2 {
    static Semaphore sem = new Semaphore(5);
    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i -> {new Thread(new Car("car"+i)).start();});
    }
    static class Car implements Runnable{
        String name;

        public Car(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try{
                sem.acquire();
                System.out.println("Now Parking: " + name);
                Thread.sleep(1000);
                System.out.println("Going away: " + name);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                sem.release();
            }
        }
    }
}

