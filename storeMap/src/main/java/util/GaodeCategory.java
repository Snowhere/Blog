package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GaodeCategory {

    public static Map<String, String> category = new HashMap<>();

   /* public static Set<String> getList() {
        if (category.isEmpty()) {
            category.put("01|02|03|04", "汽车摩托车");// 汽车摩托车
            category.put("05", "餐饮");// 餐饮
            category.put("06", "购物");// 购物
            category.put("07", "生活");// 生活
            category.put("08", "体育休闲");// 体育休闲
            category.put("09", "医疗保健");// 医疗保健
            category.put("10", "住宿");// 住宿
            category.put("11", "风景");// 风景
            category.put("12", "商务");// 商务
            category.put("13", "政府");// 政府
            category.put("14", "科教");// 科教
            category.put("15", "交通设施");// 交通设施
            category.put("16", "金融保险");// 金融保险
            category.put("17", "公司企业");// 公司企业
            category.put("20", "公共设施");// 公共设施
        }
        return category.keySet();
    }*/

    public static String getHotPlace() {
        return "190700";// 热点地名
    }

    public static String getAll() {
        return "01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20";
    }
}
