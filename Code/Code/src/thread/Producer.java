package thread;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

    private BlockingQueue<String> queue = null;
    private int id;
    private int count = 1;

    public Producer(int id, BlockingQueue<String> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100000; i++) {
                queue.put("生产者" + id + ":生产" + count++);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
