import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {
    private volatile long lastStat = System.currentTimeMillis();

    public static void main(String args[]) {

        Executor executor = Executors.newFixedThreadPool(10);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                   throw new RuntimeException("test");
                    } catch (Exception e) {
                        System.out.println("catch");
                    }
                }
            });
        try {
            executor.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
