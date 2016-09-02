package model;

import java.util.List;
import lombok.Data;

@Data
public class Response {
    //0：请求失败；1：请求成功
    private int status;

    //搜索方案数目,最大值为1000
    private int count;

    //status为0时，info返回错误原因，否则返回“OK”。详情参阅info状态表
    private String info;

    private String infocode;

    private List<Suggestion> suggestion;

    private List<POI> pois;

    @Data
    class Suggestion {

        private List<String> keywords;

        private List<String> cities;
    }
}
