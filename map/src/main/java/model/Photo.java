package model;

import java.util.List;
import lombok.Data;

@Data
public class Photo {

    private List<String> title;
    private String url;
}
