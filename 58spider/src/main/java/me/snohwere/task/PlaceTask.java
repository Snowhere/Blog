package me.snohwere.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.queue.MyQueue;

/**
 * 全北京二级分区信息
 * @author STH
 * @date 2016年6月20日
 */
public class PlaceTask extends Task {

    private static String URL = "http://bj.58.com/";

    private String area;

    public PlaceTask(String code, String area) {
        super(URL + code + "/shangpucz");
        this.area = area;
    }

    @Override
    public void process(Document doc) throws Exception {
        Element areas = doc.getElementsByClass("subarea").get(0);
        Elements urls = areas.getElementsByTag("a");
        for (Element url : urls) {
            String code = url.attr("href").split("/")[1];
            String place = url.html();
            // 保存地区信息
            //Area model = new Area();
            //model.set("code", code);
            //model.set("area", area);
            //model.set("place", place);
            //MyQueue.DATA_QUEUE.put(model);
            //创建详细任务
            MyQueue.TASK_QUEUE.put(new StoreListTask(code, 1, area, place));
        }
    }
}
