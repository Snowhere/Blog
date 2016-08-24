package me.snohwere.spider;

import java.io.IOException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.snohwere.queue.MyQueue;
import me.snohwere.task.Task;
/**
 * 用WebClient的Spider
 * @author STH
 * @date 2016年8月25日
 */
public class Spider extends Thread {

    private WebClient client;
    private Task task;
    private int num = 0;
    private int id;

    public Spider(int id) {
        client = new WebClient();
        // 设置webClient的相关参数
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getJavaScriptEngine().shutdown();
        
        this.id = id;
    }

    @Override
    public void run() {
        // 模拟浏览器打开一个目标网址
        HtmlPage rootPage;
        while (true) {
            try {
                System.out.println("线程" + id + "正在处理" + num++);
                //if(num%10==0)System.gc();
                task = MyQueue.TASK_QUEUE.take();
                try {
                    rootPage = client.getPage(task.getUrl());
                 
                    task.process(rootPage);
                    task = null;
                } catch (FailingHttpStatusCodeException | IOException e) {
                    MyQueue.TASK_QUEUE.put(task);
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
