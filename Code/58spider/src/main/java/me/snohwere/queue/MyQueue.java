package me.snohwere.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.jfinal.plugin.activerecord.Model;
import me.snohwere.task.Task;

public class MyQueue {

    public static BlockingQueue<Task> TASK_QUEUE = new LinkedBlockingQueue<>();
    public static BlockingQueue<Task> ANOTHER_TASK_QUEUE = new LinkedBlockingQueue<>();
    public static BlockingQueue<Model<? extends Model<?>>> DATA_QUEUE = new LinkedBlockingQueue<>();
    public static BlockingQueue<Model<? extends Model<?>>> ANOTHER_DATA_QUEUE = new LinkedBlockingQueue<>();

}
