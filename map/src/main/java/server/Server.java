package server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import model.Hot;
import model.POI;
import model.POIs;
import task.GetCall;
import util.GaodeCategory;

public class Server {

    private String convertUrl = "http://restapi.amap.com/v3/assistant/coordinate/convert?";

    private String searchUrl = "http://restapi.amap.com/v3/place/around?";

    private String detailUrl = "http://restapi.amap.com/v3/place/detail?";

    private String webUrl = "http://i.amap.com/detail/";

    private String key = "b04a1fecf97cf2b8c5dd6c1ded5bb2c8";

    private String hotUrl = "http://i.amap.com/service/aoi-index?";

    private PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    private CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(manager).build();

    /**
     * 
     * @param location 中心点坐标(格式:维度,经度)小数点后不超过6位
     * @param radius 半径
     */
    public POIs getInfo(String location, int radius) {
        POIs response = new POIs();
        int offset = 25;
        int page = 1;
        String types = GaodeCategory.getHotPlace();
        String getUrl = searchUrl + "output=json&extensions=all&key=" + key
            + "&location=" + location + "&radius=" + radius + "&types=" + types
            + "&offset=" + offset + "&page=" + page;
        HttpGet httpget = new HttpGet(getUrl);
        ExecutorService executor = Executors.newCachedThreadPool();
        GetCall<POIs> task = new GetCall<POIs>(httpClient, httpget, POIs.class);
        Future<POIs> result = executor.submit(task);
        try {
            response = result.get();
        } catch (InterruptedException | ExecutionException
            | UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 
     * @param location 中心点坐标(格式:维度,经度)小数点后不超过6位
     * @param radius 半径
     */
    public Hot getHot(String poiid) {
        Hot response = new Hot();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format
            .format(new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 2));
        String getUrl = hotUrl + "offset=7&byhour=0&refresh=1&aoiids=" + poiid
            + "&end=" + date;
        HttpGet httpget = new HttpGet(getUrl);
        ExecutorService executor = Executors.newCachedThreadPool();
        GetCall<Hot> task = new GetCall<Hot>(httpClient, httpget, Hot.class);
        Future<Hot> result = executor.submit(task);
        try {
            response = result.get();
        } catch (InterruptedException | ExecutionException
            | UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String args[]) {
        Server server = new Server();
        POIs response = server.getInfo("39.913501,116.471922", 1000);
        System.out.println("总共：" + response.getCount());
        for (POI poi : response.getPois()) {
            String id = poi.getId();
            System.out.println("id:" + id);
            System.out.println("名称:" + poi.getName());
            System.out.println("距离：" + poi.getDistance());
            Hot hot = server.getHot(poi.getId());
            List<List<Integer>> datas = hot.getData().get(id);
            int total = 0;
            for (List<Integer> data : datas) {
                total += data.get(3);
                // System.out.println(data.get(3));
            }
            System.out.println("指数：" + total / datas.size());
        }
    }
}
