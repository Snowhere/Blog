package me.snohwere.spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 测试spider
 * @author STH
 * @date 2016年8月25日
 */
public class TestSpider extends Thread {

    @Override
    public void run() {
        try {
            // 模拟一个浏览器
            WebClient webClient = new WebClient();
            // 设置webClient的相关参数
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            // 模拟浏览器打开一个目标网址
            HtmlPage page = webClient
                .getPage("https://www.baidu.com/p/%E5%B0%8F%E7%99%BDzlp/detail");
            Document document = Jsoup.parse(page.asXml());
            Elements keys = document.getElementsByClass("profile-attr");
            String value = "";
            String userName = "陌子显";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
