package util;

import java.util.ArrayList;
import java.util.List;

public class GaodeCategory {

    private static List<String> categoryList = new ArrayList<>();

    public static List<String> getList() {
        if (categoryList.isEmpty()) {
            categoryList.add("汽车");
            categoryList.add("餐饮");
            categoryList.add("购物");
            categoryList.add("生活");
            categoryList.add("体育休闲");
            categoryList.add("医疗保健");
            categoryList.add("住宿");
            categoryList.add("风景");
            categoryList.add("商务");
            categoryList.add("政府");
            categoryList.add("科教");
            categoryList.add("交通设施");
            categoryList.add("金融保险");
            categoryList.add("公司企业");
            categoryList.add("公共设施");
        }
        return categoryList;
    }
}
