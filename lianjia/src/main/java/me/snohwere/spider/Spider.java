package me.snohwere.spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

import me.snohwere.queue.MyQueue;
import me.snohwere.task.Task;

/**
 * 用WebClient的Spider
 * @author STH
 * @date 2016年8月25日
 */
public class Spider extends Thread {

    private WebClient client;
    private ProxyConfig proxyConfig;
    private Task task;
    
    private int id;
    private int num = 0;
    private int ip = 2;
    public Spider(int id) {
        client = new WebClient();
        proxyConfig = new ProxyConfig();
        // 设置webClient的相关参数
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getJavaScriptEngine().shutdown();
        client.getOptions().setTimeout(10000);
        proxyConfig.setProxyPort(1080);
        proxyConfig.setProxyHost("114.35.74.8");
        client.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
        client.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        client.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        client.addRequestHeader("Connection", "keep-alive");
        client.getOptions().setProxyConfig(proxyConfig);
        try {
            client.addCookie("lianjia_uuid", new URL("http://bj.lianjia.com"), "225953d1c48e64bfdb59a13d0089569a");
            client.addCookie("lianjia_ssid", new URL("http://bj.lianjia.com"), "d9dd6be8-7539-43f1-919e-efa9370aabd1");
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.id = id;
    }

    @Override
    public void run() {
        // 模拟浏览器打开一个目标网址
        HtmlPage rootPage;
        try {
            task = MyQueue.TASK_QUEUE.poll(60, TimeUnit.SECONDS);
            while (task != null) {
                System.out.println("线程" + id + "正在处理" + num++);
               /* if (num%10==0) {
                    proxyConfig.setProxyHost("192.168.1."+ip);
                    client.getOptions().setProxyConfig(proxyConfig);
                    if(ip++>256){
                        ip=2;
                    }
                }*/
                //if(num%10==0)System.gc();
                wait(100);
                try {
                    rootPage = client.getPage(task.getUrl());
                    task.process(rootPage);
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
