package me.snohwere.spider;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;

import me.snohwere.queue.MyQueue;
import me.snohwere.task.Task;

/**
 * 用HTTP请求的spider
 * @author STH
 * @date 2016年8月25日
 */
public class HttpSpider extends Thread {

    private CloseableHttpClient httpclient;

    private int num = 0;

    private int id;

    public HttpSpider(int id) {
        httpclient = HttpClients.createDefault();
        this.id = id;
    }

    @Override
    public void run() {
        HttpGet httpget;
        CloseableHttpResponse response;
        Task task = null;
        while (true) {
            System.out.println("线程" + id + "正在处理" + num++);
            try {
                task = MyQueue.TASK_QUEUE.take();
                httpget = new HttpGet(task.getUrl());
                response = httpclient.execute(httpget);
                task.process(
                    Jsoup.parse(response.getEntity().getContent().toString()));
            } catch (Exception e) {
                System.out.println(task.getUrl());
                System.out.println(e.getMessage());
            }
        }
    }
}
