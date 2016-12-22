package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 一级分区信息
 * @author STH
 * @date 2016年6月20日
 */
public class AreaTask extends Task {

    private static String URL = "http://bj.lianjia.com/xiaoqu/";

    public AreaTask() {
        super(URL);
    }

    @Override
    public void process(Document doc) throws Exception {
        System.out.println(doc.html());
        Element table = doc.getElementsByAttributeValue("data-role", "ershoufang")
            .get(0);
        Elements urls = table.getElementsByTag("a");
        for (int i = 1; i < urls.size(); i++) {
            String code = urls.get(i).attr("href").split("/")[2];
            MyQueue.TASK_QUEUE.put(new PlaceTask(code));
        }
    }
}
