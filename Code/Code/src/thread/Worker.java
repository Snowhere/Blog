package thread;

import java.util.concurrent.Callable;

public class Worker implements Callable {
    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName()+"-"+i);
        }
        return null;
    }
}
