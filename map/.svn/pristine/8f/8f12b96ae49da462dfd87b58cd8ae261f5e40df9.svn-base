package run;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;

import model.AreaModel;
import model.BizExt;
import model.POI;
import model.POIModel;
import model.Response;
import util.GaodeCategory;

public class Task {
    //经纬
    private double longitude = 0;

    private double latitude = 0;

    //默认间隔
    public static double DISTANCE = 0.004;

    //北京范围
    public static double MAX_LNG = 116.715666;//右

    public static double MIN_LNG = 116.052341;//左

    public static double MAX_LAT = 40.248446;//上

    public static double MIN_LAT = 39.725344;//下

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

    /**
     * 纬度固定间距,经度动态间距,由左上开始一行一行遍历,纬度递减,经度递增
     * @param lng 经
     * @param lat 纬
     * @param distance 经度间隔
     * @throws IOException
     */
    public void getArea(double lng, double lat, double distance) throws IOException {

        while (true) {
            //全部行遍历完
            if (lat <= MIN_LAT) {
                System.out.println("全部行遍历完");
                break;
            }
            // 当前行遍历完,换下一行
            if (lng >= MAX_LNG) {
                lng = MIN_LNG;
                lat = lat - DISTANCE;
                continue;
            }
            //当前行,下一区域
            double rightLng = (lng + distance) > MAX_LNG ? MAX_LNG
                : (lng + distance);
            double rightLat = (lat - DISTANCE) < MIN_LAT ? MIN_LNG
                : (lat - DISTANCE);
            String polygon = lng + "," + lat + ";" + rightLng + "," + rightLat;
            String getUrl = searchUrl
                + "output=json&extensions=all&offset=25&key=b04a1fecf97cf2b8c5dd6c1ded5bb2c8"
                + "&polygon=" + URLEncoder.encode(polygon, "utf8") + "&types="
                + URLEncoder.encode(types, "utf8");
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
                break;
            }
            //区域内poi数量过多,无法完全显示,缩小区域范围
            if (pois.getCount() == 1000) {
                distance /= 2;
                //本次结果不处理,重新获取
                continue;
            }
            //区域poi数量过少,下次稍微增加区域范围
            if (pois.getCount() < 300) {
                distance *= 2;
            }
            //处理本次结果
            new AreaModel().set("leftlng", lng).set("leftlat", lat)
                .set("rightlng", rightLng).set("rightlat", rightLat)
                .set("distance", distance).set("num", pois.getCount()).save();
            //遍历下一块区域
            lng = rightLng;
        }
    }

    /**
     * 分页遍历poi
     * @param url
     * @param polygon
     * @param page
     * @throws IOException
     */
    public boolean getPois(String polygon, int areaId, int page) throws IOException {
        String getUrl = searchUrl
            + "output=json&extensions=all&offset=25&key=b04a1fecf97cf2b8c5dd6c1ded5bb2c8"
            + "&polygon=" + URLEncoder.encode(polygon, "utf8") + "&types="
            + URLEncoder.encode(types, "utf8");
        HttpGet httpget = new HttpGet(getUrl + "&page=" + page);
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
            return false;
        }

        for (POI poi : pois.getPois()) {
            // save
            POIModel model = new POIModel().set("poiid", poi.getId())
                .set("name", poi.getName()).set("type", poi.getType())
                .set("tag", poi.getTag()).set("typecode", poi.getTypecode())
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
            model.set("time", new Date()).set("area_id", areaId).set("page", page)
                .save();
            //System.out.println(poi.getName());
        }
        return true;
    }
}
