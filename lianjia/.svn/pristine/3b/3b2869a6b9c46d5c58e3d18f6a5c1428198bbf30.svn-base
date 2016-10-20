package me.snohwere.main;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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
            //url = "http://bj.lianjia.com/xiaoqu/";
            //url = "http://bj.lianjia.com/xiaoqu/dongcheng/";
            //url = "http://bj.lianjia.com/xiaoqu/andingmen/pg1/";
            url = "http://bj.lianjia.com/xiaoqu/1111027379462/";
            HtmlPage page = client.getPage(url);
            Document document = Jsoup.parse(page.asXml());
            //
            System.out.println(url.split("/")[4]);
            blockDetail(document);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(url);
        }
    }

    private static void area(Document doc) {
        Element table = doc.getElementsByAttributeValue("data-role", "ershoufang")
            .get(0);
        Elements urls = table.getElementsByTag("a");
        for (int i = 1; i < urls.size(); i++) {
            String code = urls.get(i).attr("href").split("/")[2];
            String name = urls.get(i).html();
            System.out.println(code + "---" + name);
        }
    }

    private static void place(Document doc) {
        Element table = doc.getElementsByAttributeValue("data-role", "ershoufang")
            .get(0).child(1);
        Elements urls = table.getElementsByTag("a");
        for (int i = 1; i < urls.size(); i++) {
            String code = urls.get(i).attr("href").split("/")[2];
            String name = urls.get(i).html();
            System.out.println(code + "---" + name);
        }
    }

    private static void blockList(Document doc) {
        Elements elements = doc.select(".total>span");
        int total = Integer.parseInt(getHtml(elements));
        System.out.println(total);

        System.out.println((total - 1) / 30 + 1);
        //每页30
        //如果有下一页
        /* Element next = doc.getElementsByClass("on").get(0);
        if (next.nextElementSibling()!=null) {
            System.out.println("has next");
        }*/
    }

    private static void blockPages(Document doc) {
        Elements divs = doc.select(".title>a");

        for (Element div : divs) {
            String url = div.attr("href");
            System.out.println(url);
        }
    }

    private static void blockDetail(Document doc) throws ParseException {
        //区域信息
        Elements elements = doc.select(".l-txt>a");
        for (Element element : elements) {
            System.out.println(element.html());
        }
        System.out
            .println("price" + getHtml(doc.getElementsByClass("xiaoquUnitPrice")));
        elements = doc.getElementsByClass("xiaoquInfoItem");
        for (Element element : elements) {
            System.out
                .println(element.child(0).html() + "---" + element.child(1).html().replaceAll("<[\\s\\S]*?>", "").replaceAll("&nbsp;", " "));
        }
        Pattern r = Pattern.compile("resblockPosition:'([0-9]{3}\\.[0-9]{6},[0-9]{2}\\.[0-9]{6})");


        // 现在创建 matcher 对象
        Matcher m = r.matcher(doc.html());
        
        if (m.find()) {
            System.out.println(m.group(1));
        }
       // System.out.println(doc.html());

    }
}
