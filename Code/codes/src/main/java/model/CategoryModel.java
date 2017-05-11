package model;

import com.jfinal.plugin.activerecord.Model;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class CategoryModel extends Model<CategoryModel> {
    public static CategoryModel dao = new CategoryModel();
    private Map<String, CategoryModel> models = new HashMap<>();

    public CategoryModel getByCode(String code) {
        if (models.containsKey(code)) {
            return models.get(code);
        } else {
            CategoryModel model = findFirst("select * from category where code=?",
                code);
            models.put(code, model);
            return model;
        }
    }
}
