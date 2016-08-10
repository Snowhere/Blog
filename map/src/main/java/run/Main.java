package run;

import java.io.IOException;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import model.POI;
import model.POIModel;

public class Main {

    public static void main(String[] args) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost:3306/map", "root",
            "qwer");
        cp.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        arp.addMapping("poi", POIModel.class);
        arp.start();

        Task task = new Task();
        //从数据库中最后一条数据获取当前爬取的url信息,然后继续爬取.
        POIModel model = POIModel.model
            .findFirst("SELECT  url_info,url FROM `poi` ORDER BY time desc");
        try {
            if (model != null) {
                String info = model.getStr("url_info");
                String[] s = info.split("-");
                double distance = Double.parseDouble(s[1]);
                int page=Integer.parseInt(s[2])+1;
                double lat = Double.parseDouble(s[0].split(";")[0].split(",")[1]);
                double lng = Double.parseDouble(s[0].split(";")[1].split(",")[0]);
                //跑完当前poi分页
                task.getByPage(model.getStr("url"), page, info);
                //继续主循环
                task.get(lng, lat, distance);
            } else {
                task.get(Task.MIN_LNG, Task.MAX_LAT, Task.DISTANCE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
