package me.snohwere.main;

import java.io.IOException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 简单测试用HTTP请求
 * @author STH
 * @date 2016年8月25日
 */
public class Spider {

    private WebClient client;

    public Spider() {
        client = new WebClient();
        // 设置webClient的相关参数
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        // client.getJavaScriptEngine().shutdown();
    }

    public void run() {
        int pageNum = 0;
        String url = "";
        // 模拟浏览器打开一个目标网址
        try {
            while (true) {
                url = "http://tieba.baidu.com/f?kw=%E9%83%91%E5%B7%9E%E5%A4%A7%E5%AD%A6&ie=utf-8&pn="
                    + pageNum;
                HtmlPage rootPage = client.getPage(url);
                if (rootPage.asXml().contains("你要找的人baby")) {
                    System.out.println(rootPage.asXml());
                }
                System.out.print(pageNum += 50);
            }
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Spider spider = new Spider();
        spider.run();
    }
}
