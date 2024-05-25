package other;

public class Übung3 {

    private volatile boolean running = true;

    public void startThread() {
        Thread endlessThread = new Thread(() -> {
            while (running) {
                try {
                    System.out.println("Thread läuft...");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread wurde unterbrochen.");
                    running = false;
                }
            }
            System.out.println("Thread wird beendet.");
        });

        endlessThread.start();

        try {
            endlessThread.join(1000);
            if (endlessThread.isAlive()) {
                System.out.println("Thread ist nach 1000ms immer noch aktiv. Sendet Interrupt...");
                running = false;
                endlessThread.interrupt();
            } else {
                System.out.println("Thread wurde innerhalb von 1000ms beendet.");
            }
        } catch (InterruptedException e) {
            System.out.println("Hauptthread wurde unterbrochen.");
        }
    }

    public static void main(String[] args) {
        new Übung3().startThread();
    }
}
