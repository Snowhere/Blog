package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Latch {
    static class LatchPlayerThread implements Runnable {
        private int id;
        private CountDownLatch latch;

        LatchPlayerThread(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            latch.countDown();
            System.out.println("player " + id + " ready");
            try {
                latch.await();
                System.out.println("player " + id + " run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class BarrierPlayerThread implements Runnable {
        private int id;
        private CyclicBarrier barrier;

        BarrierPlayerThread(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println("player " + id + " ready");
            try {
                barrier.await();
                System.out.println("player " + id + " run");
                barrier.await();
                System.out.println("player " + id + " finish");
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 100;

        CountDownLatch latch = new CountDownLatch(count);
        CyclicBarrier barrier = new CyclicBarrier(count);
        for (int i = 0; i < count; i++) {
            new Thread(new BarrierPlayerThread(i, barrier)).start();
            Thread.sleep(10);
        }
    }
}
