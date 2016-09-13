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
import me.snohwere.util.Util;

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
        //proxyConfig.setProxyPort(1080);
        //proxyConfig.setProxyHost("114.35.74.8");
        client.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        client.addRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
        client.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        client.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");
        client.addRequestHeader("Connection", "keep-alive");
        client.addRequestHeader("Upgrade-Insecure-Requests", "1");
        client.addRequestHeader("Referer", "http://bj.lianjia.com/xiaoqu/1111027380051/");
        //client.getOptions().setProxyConfig(proxyConfig);
        //try {
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","lianjia_uuid", "225953d1c48e64bfdb59a13d0089569a"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","lianjia_ssid", "f35099c5-3e2f-4ece-a81c-305d881af012"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","lianjia_token", "2.00252378c45c289cec348e51f530529747"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","sample_traffic_test", "test_50"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_jzqa", "1.4020732957968235000.1450784039.1450784039.1450784039.1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","miyue_hide", "%20index%20"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","Hm_lvt_678d9c31c57be1c528ad7f62e5123d56", "1473345551"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","Hm_lpvt_678d9c31c57be1c528ad7f62e5123d56", "1473345551"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","Hm_lvt_efa595b768cc9dc7d7f9823368e795f1", "1473345552"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","Hm_lpvt_efa595b768cc9dc7d7f9823368e795f1", "1473345552"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","all-lj", "0a26bbdedef5bd9e71c728e50ba283a3"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_gat", "1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_gat_past", "1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_gat_global", "1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_gat_new_global", "1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_gat_dianpu_agent", "1"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_smt_uid", "56793526.15dd4532"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","CNZZDATA1253477573", "381594779-1458312303-null%7C1473346453"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","CNZZDATA1254525948", "212634324-1458309326-null%7C1473343458"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","CNZZDATA1255633284", "514015329-1458312593-null%7C1473344465"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","CNZZDATA1255604082", "603645467-1458313323-null%7C1473347244"));
            client.getCookieManager().addCookie(new Cookie(".lianjia.com","_ga", "GA1.2.647455279.1450784038"));
            
            /*client.addCookie("lianjia_uuid", new URL("http://bj.lianjia.com"), "225953d1c48e64bfdb59a13d0089569a");
            client.addCookie("lianjia_ssid", new URL("http://bj.lianjia.com"), "f35099c5-3e2f-4ece-a81c-305d881af012");
            client.addCookie("lianjia_token", new URL("http://bj.lianjia.com"), "2.00252378c45c289cec348e51f530529747");
            client.addCookie("sample_traffic_test", new URL("http://bj.lianjia.com"), "test_50");
           */ 
       // } catch (MalformedURLException e) {
        //    e.printStackTrace();
       // }
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
                client.addRequestHeader("User-Agent", Util.getRandomAgent());
                
               /* if (num%10==0) {
                    proxyConfig.setProxyHost("192.168.1."+ip);
                    client.getOptions().setProxyConfig(proxyConfig);
                    if(ip++>256){
                        ip=2;
                    }
                }*/
                //if(num%10==0)System.gc();
                
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
