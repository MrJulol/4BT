package Two;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> task = () -> {
            String threadName = Thread.currentThread().getName();
            return "Running in thread: " + threadName;
        };

        try {
            for(int i = 0;i<5;i++){
                Future<String> future = executorService.submit(task);
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
//Runnable: Verwenden, wenn eine Aufgabe keine Rückgabe benötigt, z.B. das Protokollieren von Nachrichten.
//Callable: Verwenden, wenn eine Aufgabe einen Rückgabewert benötigt, z.B. das Berechnen eines Ergebnisses.