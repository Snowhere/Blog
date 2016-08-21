package model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

import util.GaodeCategory;

@SuppressWarnings("serial")
public class POIModel extends Model<POIModel> {
    public static POIModel model = new POIModel();

    public List<POIModel> getAround(String lng, String lat, int radius) {
        return find(
            "SELECT name,typecode,location FROM poi  WHERE st_distance (point, point(?,?)) * 6371000 * PI() < 180*? and weight=1;",
            lng, lat, radius);
    }

    public List<POIModel> getHotPlace(String lng, String lat, int radius) {
        return find(
            "SELECT poiid,name,location FROM poi  WHERE st_distance (point, point(?,?)) * 6371000 * PI() < 180*? and typecode=?;",
            lng, lat, radius, GaodeCategory.getHotPlace());
    }
}
