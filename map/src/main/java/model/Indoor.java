package model;

import java.util.List;
import lombok.Data;

@Data
public class Indoor {

    // 如果当前POI为建筑物类POI，则cpid为自身POI ID；如果当前POI为商铺类POI，则cpid为其所在建筑物的POI ID
    private List<String> cpid;
    // 一般会用数字表示，例如8
    private List<String> floor;
    // 一般会带有字母，例如F8
    private List<String> truefloor;
}
