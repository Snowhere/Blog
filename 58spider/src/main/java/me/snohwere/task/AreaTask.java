package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 全北京一级分区信息
 * @author STH
 * @date 2016年6月20日
 */
public class AreaTask extends Task {

    private static String URL = "http://bj.58.com/shangpucz/";

    public AreaTask() {
        super(URL);
    }

    @Override
    public void process(Document doc) throws Exception {
        Element table = doc.getElementsByClass("pars").get(0);
        Elements urls = table.getElementsByTag("tr").get(0).getElementsByTag("a");
        for (int i = 1; i < urls.size(); i++) {
            String code = urls.get(i).attr("href").split("/")[1];
            String area = urls.get(i).html();
            MyQueue.TASK_QUEUE.put(new PlaceTask(code, area));
        }
    }
}
