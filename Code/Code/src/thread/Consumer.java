package thread;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

    private BlockingQueue<String> queue = null;
    private int id;

    public Consumer(int id, BlockingQueue<String> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        String message;
        try {
            while (true) {
                message = queue.take();
                System.out.println("消费者" + id + "处理" + message);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
