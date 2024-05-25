import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Flughafen {


    private static final int NR_OF_FLIEGER = 5;
    private static final int NR_OF_LANDEBAHNEN = 3;


    public static void main(String[] args) {
        Tower tower = new Tower(NR_OF_LANDEBAHNEN);
        Feuerwehr feuerwehr = new Feuerwehr();
        ArrayList<Thread> flieger = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(NR_OF_FLIEGER);


        for(int i = 0; i<NR_OF_FLIEGER; i++){
            flieger.add(new Thread(new Flieger(i, tower, latch, feuerwehr)));
        }
        flieger.forEach(Thread::start);

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Alle Flugzeuge sind am Boden");
            System.out.println("Flughafensimulation abgeschlossen");
        }
    }
}
