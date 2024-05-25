package other;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Parkhaus {
    static Semaphore sem = new Semaphore(5);
    public static void main(String[] args) {
        List<String> cars = new ArrayList<>();
        for(int i = 0; i<10; i++)cars.add("car"+i);
        cars.forEach(car -> {new Thread(()-> {
            try{
                sem.acquire();
                System.out.println("Now Parking: " + car);
                Thread.sleep(1000);
                System.out.println("Going away: " + car);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally {
                sem.release();
            }
        }).start();});
    }
}
