package thread;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;

import model.BizExt;
import model.POI;
import model.POIModel;
import model.Response;

public class GetThread extends Thread {
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public GetThread() {
    }

    @Override
    public void run() {
        String url = "";

        while (true) {
            try {
                url = MyQueue.URL_QUEUE.take();

                HttpGet httpget = new HttpGet(url);
                Response pois = null;
                try {
                    CloseableHttpResponse response = httpClient.execute(httpget);
                    pois = JSON.parseObject(response.getEntity().getContent(),
                        Response.class);
                    response.close();
                } catch (UnsupportedOperationException | IOException e) {
                    e.printStackTrace();
                }

                //请求失败
                if (pois == null || pois.getStatus() == 0) {
                    //System.out.println(pois.getInfo());
                    return;
                }

                for (POI poi : pois.getPois()) {
                    // save
                    POIModel model = new POIModel().set("poiid", poi.getId())
                        .set("name", poi.getName()).set("type", poi.getType())
                        .set("tag", poi.getTag()).set("typecode", poi.getTypecode())
                        .set("biz_type", poi.getBiz_type())
                        .set("address", poi.getAddress())
                        .set("location", poi.getLocation()).set("tel", poi.getTel())
                        .set("adcode", poi.getAdcode())
                        .set("adname", poi.getAdname())
                        .set("gridcode", poi.getGridcode())
                        .set("navi_poiid", poi.getNavi_poiid())
                        .set("business_area", poi.getBusiness_area());
                    BizExt ext = poi.getBiz_ext();
                    if (ext != null) {
                        model.set("rating", ext.getRating())
                            .set("cost", ext.getCost()).set("star", ext.getStar())
                            .set("hotel_ordering", ext.getHotel_ordering())
                            .set("lowest_price", ext.getLowest_price());
                    }
                    model.set("time", new Date()).set("url", url);
                    MyQueue.DATA_QUEUE.put(model);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
