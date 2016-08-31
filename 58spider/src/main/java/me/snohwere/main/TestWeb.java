package me.snohwere.main;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jfinal.kit.StrKit;

import me.snohwere.model.Store;
import me.snohwere.queue.MyQueue;
import me.snohwere.task.PlaceTask;
import me.snohwere.task.StoreDetailTask;
import me.snohwere.task.StoreListTask;
import me.snohwere.util.Util;

/**
 * 简单测试用HTTP请求
 * @author STH
 * @date 2016年8月25日
 */
public class TestWeb {
    public static String getHtml(Element element) {
        if (element != null) {
            return element.html();
        } else {
            return "";
        }
    }
    public static String getHtml(Elements elements) {
        if (elements != null && !elements.isEmpty()) {
            return elements.get(0).html();
        } else {
            return "";
        }
    }

    public static void main(String args[]) {
        WebClient client = new WebClient();
        // 设置webClient的相关参数
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setTimeout(10000);
        // client.getJavaScriptEngine().shutdown();
        String url = "";
        // 模拟浏览器打开一个目标网址
        try {
            //url = "http://bj.58.com/fatou/shangpucz/pn7";
            url = "http://bj.58.com/shangpu/27015598610492x.shtml";
            HtmlPage page = client.getPage(url);
            Document document = Jsoup.parse(page.asXml());
            //storeList(document);
            storeDetail(document);
        } catch (FailingHttpStatusCodeException | IOException | ParseException e) {
            e.printStackTrace();
            System.out.println(url);
        }
    }

    private static void qu(Document document) {
        Element table = document.getElementsByClass("pars").get(0);
        Elements urls = table.getElementsByTag("tr").get(0).getElementsByTag("a");
        for (int i = 1; i < urls.size(); i++) {
            String code = urls.get(i).attr("href").split("/")[1];
            String name = urls.get(i).html();
            System.out.println(code + "---" + name);
        }
    }

    private static void area(Document doc) {
        Element areas = doc.getElementsByClass("subarea").get(0);
        Elements urls = areas.getElementsByTag("a");
        for (Element url : urls) {
            String code = url.attr("href").split("/")[1];
            String name = url.html();
            //TODO 保存地区信息
            /* Area area = new Area();
            area.set("name", name);
            MyQueue.DATA_QUEUE.put(area);*/
            //TODO 创建详细任务
            System.out.println(code + "-----" + name);
        }
    }

    private static void storeList(Document doc) {
        Elements shops = doc.select("a.t");
        for (Element shop : shops) {
            String url = shop.attr("href").split("\\?")[0];
            System.out.println(url);
        }
        //如果有下一页
        Elements next = doc.getElementsByClass("next");
        if (next != null && !next.isEmpty()) {
            System.out.println("has next");
        }
    }

    private static void storeDetail(Document doc) throws ParseException {
        Store store = new Store();
        store.set("totalLook", getHtml(doc.getElementById("totalcount")));
        String date = getHtml(doc.getElementsByClass("other"));
        if (date.contains("发布时间")) {
            store.set("date",
                Util.dateFormat.parse(date.split("<")[0].split("：")[1]));
        } else if (StrKit.notBlank(date)) {
            store.set("date", new Date());
        }
        store.set("title", getHtml(doc.select(".headline>h1")));
        store.set("content",
            getHtml(doc.getElementsByClass("maincon"))
                .replaceAll("<[\\s\\S]*?>", "").replaceAll("\n", "")
                .replaceAll(" ", "").replaceAll("&nbsp;", ""));
        String phone = getHtml(doc.getElementById("t_phone"));
        if (phone.contains("img")) {
            phone = phone.split("'")[1];
        }
        store.set("phone", phone);
        Elements infoList = doc.select(".info>li");
        String otherInfo = "";
        for (Element element : infoList) {
            String info = element.html().replaceAll("<[\\s\\S]*>", "")
                .replaceAll("&nbsp;", "");
            switch (info.split("：")[0]) {
            case "区域":
                break;
            case "租金":
                store.set("price",
                    getHtml(element.getElementsByClass("redfont")));
                break;
            case "价格":
                store.set("price",
                    getHtml(element.getElementsByClass("redfont")));
                break;
            case "地址":
                store.set("address", info.split("：")[1]);
                break;
            case "类型":
                store.set("type", info.split("：")[1]);
                break;
            case "面积":
                store.set("size", info.split("：")[1]);
                break;
            case "临近":
                store.set("near", info.split("：")[1]);
                break;
            case "历史经营":
                store.set("history", info.split("：")[1]);
                break;
            default:
                otherInfo += info + ",";
                break;
            }
        }
        System.out.println(date.split("<")[0].split("：")[1]);
    }
}
