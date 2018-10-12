package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLock {
    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        pool.submit(() -> {
            try {
                System.out.println("First");
                pool.submit(() -> System.out.println("Second")).get();
                System.out.println("Third");
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error");
            }
        });
    }
}
