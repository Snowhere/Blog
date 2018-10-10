package thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyMain {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.put("start");
        Consumer consumer1 = new Consumer(1, queue);
        Consumer consumer2 = new Consumer(2, queue);
        Consumer consumer3 = new Consumer(3, queue);
        Consumer consumer4 = new Consumer(4, queue);
        Consumer consumer5 = new Consumer(5, queue);

        Producer producer1 = new Producer(1, queue);
        Producer producer2 = new Producer(2, queue);
        Producer producer3 = new Producer(3, queue);

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
        producer1.start();
        producer2.start();
        producer3.start();

    }
}
