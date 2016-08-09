package run;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;

import model.BizExt;
import model.POI;
import model.POIModel;
import model.Response;
import util.GaodeCategory;

public class Task {
    //默认间隔
    private double distance = 0.004;

    //经纬
    private double longitude = 0;
    private double latitude = 0;

    //北京范围
    private double maxLng = 116.715666;//右
    private double minLng = 116.052341;//左
    private double maxLat = 40.248446;//上
    private double minLat = 39.725344;//下
    
    //矩形区域搜索url
    private String searchUrl = "http://restapi.amap.com/v3/place/polygon?";

    //开发者key
    //private String key = "b04a1fecf97cf2b8c5dd6c1ded5bb2c8";

    //每页记录数(最大25)
    private int offset = 25;

    //页(最大100)
    private int page = 1;

    //搜索的分类
    private String types = GaodeCategory.getAll();

    //矩形区域
    private String polygon = "116.467779,39.913470;116.471779,39.909470";

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public void get(double lng,double lat,double distance) throws IOException {
        String polygon="";
        String getUrl = searchUrl
            + "output=json&extensions=all&offset=25&key=b04a1fecf97cf2b8c5dd6c1ded5bb2c8"
            + "&polygon=" + polygon + "&types=" + types;
        HttpGet httpget = new HttpGet(getUrl);
        CloseableHttpResponse response = httpClient.execute(httpget);
        Response pois = null;
        try {
            pois = JSON.parseObject(response.getEntity().getContent(),
                Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }

        //请求失败
        if (pois == null || pois.getStatus() == 0) {
            System.out.println(pois.getInfo());
            return;
        }
        //区域内poi数量过多,无法完全显示,缩小区域范围
        if (pois.getCount() == 1000) {
            //本次结果不处理,重新获取

        }
        //区域poi数量过少,下次稍微增加区域范围
        if (pois.getCount() < 300) {
            //处理本次结果
            for (int i = 1; i < pois.getCount() / 25; i++) {
                getByPage(getUrl, polygon, i);
            }

        }
    }

    /**
     * 分页遍历poi
     * @param url
     * @param polygon
     * @param page
     * @throws IOException
     */
    public void getByPage(String url, String polygon, int page) throws IOException {
        HttpGet httpget = new HttpGet(url + "&page=" + page);
        CloseableHttpResponse response = httpClient.execute(httpget);
        Response pois = null;
        try {
            pois = JSON.parseObject(response.getEntity().getContent(),
                Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        for (POI poi : pois.getPois()) {
            // save
            POIModel model = new POIModel().set("name", poi.getName())
                .set("type", poi.getType()).set("tag", poi.getTag())
                .set("typecode", poi.getTypecode())
                .set("biz_type", poi.getBiz_type()).set("address", poi.getAddress())
                .set("location", poi.getLocation()).set("tel", poi.getTel())
                .set("adcode", poi.getAdcode()).set("adname", poi.getAdname())
                .set("gridcode", poi.getGridcode())
                .set("navi_poiid", poi.getNavi_poiid())
                .set("business_area", poi.getBusiness_area());
            BizExt ext = poi.getBiz_ext();
            if (ext != null) {
                model.set("rating", ext.getRating()).set("cost", ext.getCost())
                    .set("star", ext.getStar())
                    .set("hotel_ordering", ext.getHotel_ordering())
                    .set("lowest_price", ext.getLowest_price());
            }
            model.set("url_info", polygon + "-" + page).save();
        }
    }
}
