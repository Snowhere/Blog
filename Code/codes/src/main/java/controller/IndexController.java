package controller;

import com.jfinal.core.Controller;
import model.CategoryModel;
import model.POIModel;
import model.Response;

import java.util.List;

public class IndexController extends Controller {
    public void index(){
        renderJsp("index.jsp");
    }

    public void getAround() {
        String lng = getPara("lng");
        String lat = getPara("lat");
        int radius = getParaToInt("radius");

        Response response = new Response();
        List<POIModel> pois = POIModel.model.getAround(lng, lat, radius);
        for (POIModel poi : pois) {
            CategoryModel model = CategoryModel.dao
                .getByCode(poi.getStr("typecode").split("\\|")[0]);
            if (model != null) {
                response.push(model.getStr("first")).push(model.getStr("second"))
                    .push(model.getStr("code"), model.getStr("third")).push(poi);
            } else {
                System.out.println("error:" + poi.getStr("typecode"));
            }
        }
        renderJson(response);
    }

    public void getHotPlace() {
        String lng = getPara("lng");
        String lat = getPara("lat");
        int radius = getParaToInt("radius");

        List<POIModel> pois = POIModel.model.getHotPlace(lng, lat, radius);

        renderJson(pois);
    }

    public void getPoi() {
        String id = getPara("id");
        renderJson(POIModel.model.findFirst("select * from poi where poiid=?",id));
    }
}
