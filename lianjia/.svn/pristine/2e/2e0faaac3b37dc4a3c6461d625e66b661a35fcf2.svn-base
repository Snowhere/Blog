package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 小区列表
 * @author STH
 * @date 2016年6月20日
 */
public class BlockListTask extends Task {

    private static String URL = "http://bj.lianjia.com/xiaoqu/";

    private String place;

    public BlockListTask(String place) {
        super(URL + place + "/");
        this.place = place;
    }

    @Override
    public void process(Document doc) throws Exception {
        Elements elements = doc.select(".total>span");
        int total = Integer.parseInt(getHtml(elements));
        for (int i = 1; i <= (total - 1) / 30 + 1; i++) {
            MyQueue.TASK_QUEUE.put(new BlockPageTask(place, i));
        }
    }
}
