package me.snohwere.main;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import me.snohwere.queue.MyQueue;
import me.snohwere.task.PlaceTask;
import me.snohwere.task.StoreDetailTask;
import me.snohwere.task.StoreListTask;

/**
 * 简单测试用HTTP请求
 * @author STH
 * @date 2016年8月25日
 */
public class TestWeb {

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
            url = "http://bj.58.com/shangpu/27160683569104x.shtml";
            HtmlPage page = client.getPage(url);
            Document document = Jsoup.parse(page.asXml());
            //storeList(document);
            storeDetail(document);
        } catch (FailingHttpStatusCodeException | IOException e) {
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

    private static void storeDetail(Document doc) {
        String totalLook = doc.getElementById("totalcount").html();
        String date = doc.getElementsByClass("other").get(0).html().split("<")[0].split("：")[1];
        String title = doc.select(".headline>h1").get(0).html();
        //"<.*>"
        String content = doc.getElementsByClass("maincon").get(0).html()
            .replaceAll("<[\\s\\S]*?>", "").replaceAll("\n", "").replaceAll(" ", "")
            .replaceAll("&nbsp;", "");
        String phone = doc.getElementById("t_phone").html();
        if (phone.contains("img")) {
            phone = phone.split("'")[1];
        }
        Elements infoList = doc.select(".info>li");
        for (Element element : infoList) {
            String info = element.html().replaceAll("<[\\s\\S]*>", "")
                .replaceAll("&nbsp;", "");
            String otherInfo = "";
            switch (info.split("：")[0]) {
            case "区域":
                break;
            case "价格":
                System.out.println(
                    "价格" + element.getElementsByClass("redfont").get(0).html());

                break;
            case "地址":
                System.out.println("地址" + info.split("：")[1]);
                break;
            case "类型":
                System.out.println("类型" + info.split("：")[1]);
                break;
            case "面积":
                System.out.println("面积" + info.split("：")[1]);
                break;

            default:
                otherInfo += info + ",";
                break;
            }

        }

        //地址,类型,面积,价格
        //
        //System.out.println(phone);
        //System.out.println(title);
        // System.out.println(date);
        //System.out.println(totalLook);
    }
}
