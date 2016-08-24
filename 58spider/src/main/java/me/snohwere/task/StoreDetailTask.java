package me.snohwere.task;

import org.jsoup.nodes.Document;

import me.snohwere.model.Store;
import me.snohwere.queue.MyQueue;

/**
 * 商铺详情信息
 * @author STH
 * @date 2016年6月20日
 */
public class StoreDetailTask extends Task {

    public StoreDetailTask(String url) {
        super(url);
    }

    @Override
    public void process(Document doc) {
        try {

            //TODO 保存
            MyQueue.DATA_QUEUE.put(new Store());

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
