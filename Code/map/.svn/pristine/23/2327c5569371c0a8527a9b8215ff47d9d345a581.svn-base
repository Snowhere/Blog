package thread;

import java.util.LinkedList;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class DataThread extends Thread {

    @SuppressWarnings("rawtypes")
    private Model model;
    private int num = 0;
    private int id;

    public DataThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        batchSave();
    }

    private void save() {
        while (true) {
            System.out.println("数据库线程" + id + "正在处理" + num++);
            try {
                model = MyQueue.DATA_QUEUE.take();
                model.save();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        while (true) {
            System.out.println("数据库线程" + id + "正在处理" + num++);
            try {
                model = MyQueue.DATA_QUEUE.take();
                model.update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void batchSave() {
        LinkedList<Model<? extends Model<?>>> list = new LinkedList<>();
        while (true) {
            MyQueue.DATA_QUEUE.drainTo(list);
            // list.add(MyQueue.DATA_QUEUE.poll(timeout, unit));
            //System.out.println("数据库线程" + id + "正在处理" + num++);
            Db.batchSave(list, 100);
            list.clear();
        }
    }
}
