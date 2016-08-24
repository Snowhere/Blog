package me.snohwere.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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

    public void infoLog(String message) {
        System.out.println(message);
    }

    public void errorLog(String title, String message) {
    }
}
