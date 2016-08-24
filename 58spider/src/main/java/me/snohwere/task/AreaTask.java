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
public class AreaTask extends Task {

    private static String URL = "http://bj.58.com/";
    private String name;

    public AreaTask(String code, String name) {
        super(URL + code + "/shangpucz");
        this.name = name;
    }

    @Override
    public void process(Document doc) {
        try {

            Element areas = doc.getElementsByClass("subarea").get(0);
            Elements urls = areas.getElementsByTag("a");
            for (Element url : urls) {
                String code = url.attr("href").split("/")[1];
                String name = url.html();
                //TODO 保存地区信息
                /* Area area = new Area();
                area.set("name", name);
                MyQueue.DATA_QUEUE.put(area);*/
                //TODO 创建详细任务
                MyQueue.TASK_QUEUE.put(new StoreListTask(code, 1));
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
