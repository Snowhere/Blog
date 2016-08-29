package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 商铺列表
 * @author STH
 * @date 2016年6月20日
 */
public class StoreListTask extends Task {

    private static String URL = "http://bj.58.com/";
    private String code;
    private String area;
    private String place;
    private int page;

    public StoreListTask(String code, int page, String area, String place) {
        super(URL + code + "/shangpucz/pn" + page);
        this.code = code;
        this.page = page;
        this.area = area;
        this.place = place;
    }

    @Override
    public void process(Document doc) {
        try {
            Elements shops = doc.select("a.t");
            for (Element shop : shops) {
                String url = shop.attr("href").split("\\?")[0];
                MyQueue.TASK_QUEUE.put(new StoreDetailTask(url, area, place));
            }
            //如果有下一页
            Elements next = doc.getElementsByClass("next");
            if (next != null && !next.isEmpty()) {
                MyQueue.TASK_QUEUE
                    .put(new StoreListTask(code, page + 1, area, place));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
