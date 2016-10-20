package me.snohwere.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.snohwere.model.Block;
import me.snohwere.queue.MyQueue;

/**
 * 小区详情信息
 * @author STH
 * @date 2016年6月20日
 */
public class BlockDetailTask extends Task {

    public BlockDetailTask(String url) {
        super(url);
    }

    @Override
    public void process(Document doc) throws Exception {
        Block block = new Block();
        block.set("code", getUrl().split("/")[4]);
        //区域信息
        Elements elements = doc.select(".l-txt>a");
        block.set("area", elements.get(2).html());
        block.set("place", elements.get(3).html());
        block.set("name", elements.get(4).html());
        block.set("price", getHtml(doc.getElementsByClass("xiaoquUnitPrice")));
        elements = doc.getElementsByClass("xiaoquInfoItem");
        String others = "";
        for (Element element : elements) {
            switch (element.child(0).html()) {
            case "建筑年代":
                block.set("year", element.child(1).html());
                break;
            case "建筑类型":
                block.set("type", element.child(1).html());
                break;
            case "物业费用":
                block.set("charge", element.child(1).html());
                break;
            case "物业公司":
                block.set("company", element.child(1).html());
                break;
            case "开发商":
                block.set("developer", element.child(1).html());
                break;
            case "小区概况":
                block.set("info", element.child(1).html().replaceAll("&nbsp;", " "));
                break;
            case "楼栋总数":
                block.set("building", element.child(1).html());
                break;
            case "房屋总数":
                block.set("house", element.child(1).html());
                break;
            case "附近门店":
                block.set("near", element.child(1).html()
                    .replaceAll("<[\\s\\S]*?>", "").replaceAll("&nbsp;", " "));
                break;
            default:
                others += others + element.child(0).html() + ","
                    + element.child(1).html();
                break;
            }
        }
        block.set("others", others);

        //匹配坐标
        Pattern r = Pattern
            .compile("resblockPosition:'([0-9]{3}\\.[0-9]{6},[0-9]{2}\\.[0-9]{6})");
        Matcher m = r.matcher(doc.html());
        if (m.find()) {
            block.set("position", m.group(1));
        }

        MyQueue.DATA_QUEUE.put(block);
    }
}
