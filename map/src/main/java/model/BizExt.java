package model;

import lombok.Data;

@Data
public class BizExt {

    // 评分
    private String rating;

    // 人均消费
    private String cost;

    private String star;

    private String hotel_ordering;

    private String lowest_price;
}
