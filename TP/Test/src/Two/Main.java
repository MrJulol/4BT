package Two;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Running in thread: " + threadName);
        };

        for (int i = 0; i < 5; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
    }
}

