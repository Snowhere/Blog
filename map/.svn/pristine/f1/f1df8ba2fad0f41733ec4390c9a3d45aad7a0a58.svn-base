package run;

import java.io.IOException;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import model.AreaModel;
import model.POIModel;

public class Main {

    public static void main(String[] args) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost:3306/map", "root",
            "qwer");
        cp.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        arp.addMapping("poi", POIModel.class);
        arp.addMapping("area", AreaModel.class);
        arp.start();

        //getArea();
        //从数据库中最后一条数据获取当前爬取的url信息,然后继续爬取.
        //getPois();
    }

    private static void getArea() {
        Task task = new Task();
        AreaModel area = AreaModel.model
            .findFirst("select * from area order by id desc");
        try {
            if (area == null) {
                task.getArea(Task.MIN_LNG, Task.MAX_LAT, Task.DISTANCE);
            } else {
                task.getArea(area.getDouble("rightlng"), area.getDouble("leftlat"),
                    area.getDouble("distance"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getPois() {
        Task task = new Task();
        POIModel model = POIModel.model
            .findFirst("SELECT  * FROM `poi` ORDER BY id desc limit 1");
        int areaId = 1;
        int page = 1;
        if (model != null) {
            areaId = model.getInt("area_id");
            page = model.getInt("page") + 1;
        }
        while (areaId <= 2803) {
            //获取区域块
            AreaModel area = AreaModel.model.findById(areaId);
            int num = area.getInt("num");
            String polygon = area.getDouble("leftlng") + ","
                + area.getDouble("leftlat") + ";" + area.getDouble("rightlng") + ","
                + area.getDouble("rightlat");
            try {
                System.out.println(areaId);
                //分页查poi
                for (int i = page; i <= (num - 1) / 25 + 1; i++) {
                    task.getPois(polygon, areaId, i);
                }
                //继续主循环
                areaId += 1;
                page = 1;
            } catch (IOException e) {
                e.printStackTrace();
                model = POIModel.model
                    .findFirst("SELECT  * FROM `poi` ORDER BY id desc limit 1");
                areaId = model.getInt("area_id");
                page = model.getInt("page") + 1;
            }
        }
    }
}
