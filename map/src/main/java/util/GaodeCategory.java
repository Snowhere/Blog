package util;

import java.util.ArrayList;
import java.util.List;

public class GaodeCategory {

    private static List<String> categoryList = new ArrayList<>();

    public static List<String> getList() {
        if (categoryList.isEmpty()) {
            categoryList.add("01|02|03|04");// 汽车摩托车
            categoryList.add("05");// 餐饮
            categoryList.add("06");// 购物
            categoryList.add("07");// 生活
            categoryList.add("08");// 体育休闲
            categoryList.add("09");// 医疗保健
            categoryList.add("10");// 住宿
            categoryList.add("11");// 风景
            categoryList.add("12");// 商务
            categoryList.add("13");// 政府
            categoryList.add("14");// 科教
            categoryList.add("15");// 交通设施
            categoryList.add("16");// 金融保险
            categoryList.add("17");// 公司企业
            categoryList.add("20");// 公共设施
        }
        return categoryList;
    }

    public static String getHotPlace() {
        return "190700";// 热点地名
    }

    public static String getAll() {
        return "01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20";
    }
}
