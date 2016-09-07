package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 小区分页列表
 * @author STH
 * @date 2016年6月20日
 */
public class BlockPageTask extends Task {

    private static String URL = "http://bj.lianjia.com/xiaoqu/";

    public BlockPageTask(String place, int page) {
        super(URL + place + "/pg" + page + "/");
    }

    @Override
    public void process(Document doc) throws Exception {
        Elements divs = doc.select(".title>a");
        for (Element div : divs) {
            String url = div.attr("href");
            MyQueue.TASK_QUEUE.put(new BlockDetailTask(url));
        }
    }
}
