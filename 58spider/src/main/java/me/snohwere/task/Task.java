package me.snohwere.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jfinal.plugin.activerecord.Model;

public abstract class Task {

    protected String url;
    protected Model<? extends Model<?>> model;

    public Task(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    };

    public void process(HtmlPage page) {
        process(Jsoup.parse(page.asXml()));
    };

    public abstract void process(Document doc);

    public String getHtml(Element element) {
        if (element != null) {
            return element.html();
        } else {
            return "";
        }
    }

    public String getHtml(Elements elements) {
        if (elements != null && !elements.isEmpty()) {
            return elements.get(0).html();
        } else {
            return "";
        }
    }

    public void infoLog(String message) {
        System.out.println(message);
    }

    public void errorLog(String title, String message) {
    }
}
