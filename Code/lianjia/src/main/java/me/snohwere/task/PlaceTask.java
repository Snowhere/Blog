package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 二级分区信息
 * @author STH
 * @date 2016年6月20日
 */
public class PlaceTask extends Task {

    private static String URL = "http://bj.lianjia.com/xiaoqu/";
    private String area;
    public PlaceTask(String area) {
        super(URL + area + "/");
        this.area=area;
    }

    @Override
    public void process(Document doc) throws Exception {
        Element table = doc.getElementsByAttributeValue("data-role", "ershoufang")
            .get(0).child(1);
        Elements urls = table.getElementsByTag("a");
        for (Element url : urls) {
            String place = url.attr("href").split("/")[2];
            MyQueue.TASK_QUEUE.put(new BlockListTask(area,place));
        }
    }
}
