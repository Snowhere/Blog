package me.snohwere.spider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    private int id;

    public Spider(int id) {
        client = new WebClient();
        // 设置webClient的相关参数
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getJavaScriptEngine().shutdown();
        client.getOptions().setTimeout(10000);
        this.id = id;
    }

    @Override
    public void run() {
        // 模拟浏览器打开一个目标网址
        HtmlPage rootPage;
        try {
            task = MyQueue.TASK_QUEUE.poll(60, TimeUnit.SECONDS);
            while (task != null) {
                //System.out.println("线程" + id + "正在处理" + num++);
                //if(num%10==0)System.gc();
                try {
                    rootPage = client.getPage(task.getUrl());
                    task.process(rootPage);
                    task = null;
                } catch (FailingHttpStatusCodeException | IOException e) {
                    task.addTimes();
                    if (task.getTimes() < 4) {
                        MyQueue.TASK_QUEUE.put(task);
                    }
                    System.out.println(e.getMessage() + "---" + task.getUrl());
                }
                task = MyQueue.TASK_QUEUE.poll(60, TimeUnit.SECONDS);
            }
            System.out.println("线程" + id + "已停止");
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
