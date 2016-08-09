package run;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSON;

import model.Response;
import util.GaodeCategory;

public class Task {
    //间隔
    private double distance = 0.004;

    //经
    private double longitude = 0;

    //维
    private double latitude = 0;

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

    public void get() throws IOException {

        String getUrl = searchUrl
            + "output=json&extensions=all&offset=25&key=b04a1fecf97cf2b8c5dd6c1ded5bb2c8"
            + "&polygon=" + polygon + "&types=" + types + "&page=" + page;
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
        }
    }

    public void getByPage(int page) throws IOException {
        String getUrl = searchUrl
            + "output=json&extensions=all&offset=25&key=b04a1fecf97cf2b8c5dd6c1ded5bb2c8"
            + "&polygon=" + polygon + "&types=" + types + "&page=" + page;
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
    }
}
